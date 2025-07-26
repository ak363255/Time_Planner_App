package com.example.utils.platform.screenmodel.work

import com.example.utils.functional.Either
import com.example.utils.managers.CoroutineBlock
import com.example.utils.platform.screenmodel.store.BaseStore
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface  WorkScope<S : BaseViewState, A: BaseAction,F: BaseUiEffect> : WorkResultHandler<A,F>{

    fun launchBackgroundWork(
        key: BackgroundWorkKey,
        dispatcher : CoroutineDispatcher? = null,
        scope : CoroutineScope? = null,
        block : CoroutineBlock
    ): Job

    suspend fun state() : S
    suspend fun sendAction(action:A)
    suspend fun sendEffect(effect : F)

    class Base<S: BaseViewState,E: BaseEvent,A: BaseAction,F: BaseUiEffect>(
        private val store : BaseStore<S,E,A,F>,
        private val coroutineScope: CoroutineScope

    ): WorkScope<S,A,F>{

        private val backgroundWorkMap = mutableMapOf<BackgroundWorkKey, Job>()


        override fun launchBackgroundWork(
            key: BackgroundWorkKey,
            dispatcher: CoroutineDispatcher?,
            scope: CoroutineScope?,
            block: CoroutineBlock
        ): Job {
            backgroundWorkMap[key].let { job ->
                if(job != null){
                    job.cancel()
                    backgroundWorkMap.remove(key)
                }

            }
            return (scope?:coroutineScope).launch {
                dispatcher?.let { withContext(it,block) }?:block()
            }.apply {
                backgroundWorkMap[key] = this
                start()
            }


        }

        override suspend fun state(): S {
            return  store.fetchState()
        }

        override suspend fun sendAction(action: A) {
            store.handleAction(action)
        }

        override suspend fun sendEffect(effect: F) {
            store.postEffect(effect)
        }

        override suspend fun Either<A, F>.handleWork() = when(this){
            is Either.Left -> sendAction(data)
            is Either.Right -> sendEffect(data)
        }

        override suspend fun Flow<Either<A, F>>.collectAndHandleWork() = collect { it.handleWork() }

    }
}

interface BackgroundWorkKey