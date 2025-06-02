package com.example.utils.platform.communications.state

import com.example.utils.platform.communications.Communicator
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import com.example.utils.platform.screenmodel.contract.EmptyUiEffect

interface StateCommunicator<S: BaseViewState> : Communicator<S>{

    abstract class Abstract<S: BaseViewState>(defaultState:S): StateCommunicator<S>, Communicator.AbstractStateFlow<S>(defaultState)

}

interface EffectCommunicator<F: BaseUiEffect> : Communicator<F>{
    abstract class Abstract<F: BaseUiEffect>: EffectCommunicator<F>, Communicator.AbstractSharedFlow<F>(flowBufferCapacity = 1)
    class Empty<F: EmptyUiEffect> : Abstract<F>()
}