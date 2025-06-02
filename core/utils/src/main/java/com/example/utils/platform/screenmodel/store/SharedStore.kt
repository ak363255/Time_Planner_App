package com.example.utils.platform.screenmodel.store

import com.example.utils.manager.CoroutineManager
import com.example.utils.platform.communications.state.EffectCommunicator
import com.example.utils.platform.communications.state.StateCommunicator
import com.example.utils.platform.screenmodel.Actor
import com.example.utils.platform.screenmodel.Reducer
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.coroutines.flow.FlowCollector

class SharedStore<S: BaseViewState,E: BaseEvent,A: BaseAction,F: BaseUiEffect>(
    private val effectCommunicator: EffectCommunicator<F>,
    stateCommunicator: StateCommunicator<S>,
    actor: Actor<S,E,A,F>,
    reducer : Reducer<S,A>,
    coroutineManager: CoroutineManager,
): BaseStore.Abstract<S,E,A,F>(stateCommunicator,actor,reducer,coroutineManager){

    override suspend fun postEffect(effect: F) {
       effectCommunicator.update(effect)
    }
    override suspend fun collectUiEffect(collector: FlowCollector<F>) {
        effectCommunicator.collect(collector)
    }

}