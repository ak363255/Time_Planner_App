package com.example.timeplannerapp.presentation.ui.tabs.viewmodel

import com.example.timeplannerapp.presentation.ui.tabs.contract.TabViewState
import com.example.utils.platform.communications.state.StateCommunicator
import kotlinx.coroutines.flow.FlowCollector

interface TabStateCommunicator: StateCommunicator<TabViewState> {
    class Base(): TabStateCommunicator, StateCommunicator.Abstract<TabViewState>(defaultState = TabViewState())
}