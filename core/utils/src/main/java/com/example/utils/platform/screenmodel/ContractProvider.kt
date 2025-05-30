package com.example.utils.platform.screenmodel

import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.coroutines.flow.FlowCollector

interface StateProvider<S:BaseViewState>{
    suspend fun collectState(collector:FlowCollector<S>)
}

interface UiEffectProvider<F:BaseUiEffect>{
    suspend fun collectUiEffect(collector: FlowCollector<F>)
}

interface EventReceiver<E:BaseEvent>{
    fun dispatchEvent(event:E)
}

interface ContractProvider<S:BaseViewState,E:BaseEvent,F:BaseUiEffect,D:ScreenDependencies>:
        StateProvider<S>,
        UiEffectProvider<F>,
        EventReceiver<E>,
        Init<D>