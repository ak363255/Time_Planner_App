package com.example.impl.presentation.ui.home.screenModel

import com.example.impl.presentation.ui.home.contract.HomeViewState
import com.example.utils.platform.communications.state.StateCommunicator
import kotlinx.coroutines.flow.FlowCollector

interface HomeStateCommunicator : StateCommunicator<HomeViewState>{
    class Base(): HomeStateCommunicator, StateCommunicator.Abstract<HomeViewState>(defaultState = HomeViewState())
}