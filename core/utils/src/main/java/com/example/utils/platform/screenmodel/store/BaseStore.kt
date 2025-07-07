package com.example.utils.platform.screenmodel.store

import com.example.utils.managers.CoroutineManager
import com.example.utils.platform.communications.state.StateCommunicator
import com.example.utils.platform.screenmodel.Actor
import com.example.utils.platform.screenmodel.Reducer
import com.example.utils.platform.screenmodel.StateProvider
import com.example.utils.platform.screenmodel.UiEffectProvider
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import com.example.utils.platform.screenmodel.work.WorkScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex

interface BaseStore<S: BaseViewState,E: BaseEvent,A: BaseAction,F: BaseUiEffect>: StateProvider<S>,
    UiEffectProvider<F> {

    fun sendEvent(event : E)
    suspend fun postEffect(effect : F)
    suspend fun handleAction(action : A)
    suspend fun updateState(transform : suspend (S)->S)
    suspend fun fetchState():S

    abstract class Abstract<S: BaseViewState,E: BaseEvent,A: BaseAction,F: BaseUiEffect>(
        private val stateCommunicator: StateCommunicator<S>,
        private val actor : Actor<S, E, A, F>,
        private val reducer : Reducer<S, A>,
        private val coroutineManager : CoroutineManager
    ) : BaseStore<S,E,A,F>{

        private val mutex = Mutex()
        private val eventChannel = Channel<E>(Channel.Factory.UNLIMITED, BufferOverflow.SUSPEND)

        fun start(scope: CoroutineScope) = coroutineManager.runOnBackground(scope){
            val workScope = WorkScope.Base(this@Abstract,this)
            while (isActive){
                actor.apply {
                    workScope.handleEvent(eventChannel.receive())
                }
            }
        }

        override fun sendEvent(event: E) {
            eventChannel.trySend(event)
        }

        override suspend fun fetchState(): S {
            return stateCommunicator.read()
        }

        override suspend fun updateState(transform: suspend (S) -> S) {

            val state = transform(stateCommunicator.read())
            stateCommunicator.update(state)
        }

        override suspend fun handleAction(action: A) = updateState{
            reducer.reduce(action,fetchState())
        }

        override suspend fun collectState(collector: FlowCollector<S>) {
            stateCommunicator.collect(collector)
        }

    }

}