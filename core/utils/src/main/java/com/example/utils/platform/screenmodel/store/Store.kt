package com.example.utils.platform.screenmodel.store

import com.example.utils.managers.CoroutineManager
import com.example.utils.platform.communications.state.EffectCommunicator
import com.example.utils.platform.communications.state.StateCommunicator
import com.example.utils.platform.screenmodel.Actor
import com.example.utils.platform.screenmodel.Reducer
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.coroutines.CoroutineScope


fun <S: BaseViewState,E: BaseEvent,A: BaseAction,F: BaseUiEffect> MVIStore(
    effectCommunicator : EffectCommunicator<F>,
    stateCommunicator : StateCommunicator<S>,
    actor : Actor<S,E,A,F>,
    reducer: Reducer<S,A>,
    coroutineManager: CoroutineManager
) = SharedStore(effectCommunicator,stateCommunicator,actor,reducer,coroutineManager)

fun <S: BaseViewState,E: BaseEvent,A: BaseAction,F: BaseUiEffect> launchedStore(
    scope: CoroutineScope,
    effectCommunicator: EffectCommunicator<F>,
    stateCommunicator: StateCommunicator<S>,
    actor: Actor<S,E,A,F>,
    reducer: Reducer<S,A>,
    coroutineManager: CoroutineManager
) = MVIStore(
    effectCommunicator,stateCommunicator,actor,reducer,coroutineManager
).apply { start(scope) }