package com.example.timeplannerapp.presentation.ui.main.viewmodel

import com.example.timeplannerapp.presentation.ui.main.contract.MainViewState
import com.example.utils.platform.communications.state.StateCommunicator

interface MainStateCommunicator : StateCommunicator<MainViewState>{
    class Base: MainStateCommunicator,
        StateCommunicator.Abstract<MainViewState>(defaultState = MainViewState())
}