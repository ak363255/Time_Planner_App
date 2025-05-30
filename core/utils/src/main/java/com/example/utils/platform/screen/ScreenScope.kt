package com.example.utils.platform.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.utils.platform.screenmodel.ContractProvider
import com.example.utils.platform.screenmodel.ScreenDependencies
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Amit on 30-05-2025.
 */

interface ScreenScope<S: BaseViewState,E: BaseEvent,F: BaseUiEffect,D:ScreenDependencies>{
    fun dispatchEvent(event:E)

    @Composable
    fun fetchState():S

    @Composable
    fun handleEffect(block: suspend  CoroutineScope.(F)->Unit)

    class Base<S:BaseViewState,E:BaseEvent,F:BaseUiEffect,D:ScreenDependencies>(
        private val contractProvider : ContractProvider<S,E,F,D>,
        internal val initialState : S
    )
        : ScreenScope<S,E,F,D>{
        override fun dispatchEvent(event: E) {
            contractProvider.dispatchEvent(event)
        }

        @Composable
        override fun fetchState(): S {
            val state = rememberSaveable{
                mutableStateOf(initialState)
            }
            LaunchedEffect(Unit){
                contractProvider.collectState{
                    state.value = it
                }
            }
            return state.value
        }

        @Composable
        override fun handleEffect(block: suspend CoroutineScope.(F) -> Unit) {
            LaunchedEffect(Unit){
                contractProvider.collectUiEffect{effect ->
                    block(effect)
                }
            }
        }

    }
}
