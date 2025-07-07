package com.example.utils.platform.screenmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utils.managers.CoroutineManager
import com.example.utils.platform.communications.state.EffectCommunicator
import com.example.utils.platform.communications.state.StateCommunicator
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import com.example.utils.platform.screenmodel.store.launchedStore
import kotlinx.coroutines.flow.FlowCollector
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Amit on 30-05-2025.
 */
abstract class BaseViewModel<S:BaseViewState,E:BaseEvent,A:BaseAction,F:BaseUiEffect,D:ScreenDependencies>(
    protected val stateCommunicator: StateCommunicator<S>,
    protected val effectCommunicator: EffectCommunicator<F>,
    coroutineManager : CoroutineManager
)
    :ViewModel(), Reducer<S,A>, Actor<S,E,A,F>, ContractProvider<S,E,F,D>
{
    private val scope get() = viewModelScope

    protected val isInitialize = AtomicBoolean(false)

    private val store = launchedStore(
        scope = scope,
        effectCommunicator = effectCommunicator,
        stateCommunicator = stateCommunicator,
        actor = this,
        reducer = this,
        coroutineManager = coroutineManager
    )

    override fun init(deps: D) {
        isInitialize.set(true)
    }

    override fun dispatchEvent(event: E)  = store.sendEvent(event)


    override suspend fun collectState(collector: FlowCollector<S>) {
        store.collectState(collector)
    }

    override suspend fun collectUiEffect(collector: FlowCollector<F>) {
        store.collectUiEffect(collector)
    }
}