package com.example.utils.platform.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.utils.platform.screenmodel.ContractProvider
import com.example.utils.platform.screenmodel.ScreenDependencies
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState

/**
 * Created by Amit on 30-05-2025.
 */

@Composable
fun <S : BaseViewState, E : BaseEvent, F : BaseUiEffect, D : ScreenDependencies, CP : ContractProvider<S, E, F, D>> ScreenContent(
    screenModel: CP,
    initialState: S,
    dependencies: D,
    content: @Composable ScreenScope<S, E, F, D>.(state: S) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        screenModel.init(dependencies)
    }
    val screenScope = rememberScreenScope(
        contractProvider = screenModel,
        initialState = initialState
    )
    content.invoke(screenScope,screenScope.fetchState())
}

@Composable
fun <S : BaseViewState, E : BaseEvent, F : BaseUiEffect, D : ScreenDependencies> rememberScreenScope(
    contractProvider: ContractProvider<S, E, F, D>,
    initialState: S,
): ScreenScope<S, E, F, D> {
    return rememberSaveable(
        saver = screenScopeSaver(
            contractProvider
        )
    ) {
        ScreenScope.Base(contractProvider, initialState)
    }
}

private fun <S : BaseViewState, E : BaseEvent, F : BaseUiEffect, D : ScreenDependencies> screenScopeSaver(
    conttractProvider: ContractProvider<S, E, F, D>,
): Saver<ScreenScope.Base<S, E, F, D>, S> = object : Saver<ScreenScope.Base<S, E, F, D>, S> {
    override fun restore(value: S): ScreenScope.Base<S, E, F, D>? {
        return ScreenScope.Base(conttractProvider, value)
    }

    override fun SaverScope.save(value: ScreenScope.Base<S, E, F, D>): S? {
        return value.initialState
    }
}