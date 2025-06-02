package com.example.timeplannerapp.presentation.ui.main.viewmodel

import com.example.timeplannerapp.presentation.ui.main.contract.MainAction
import com.example.timeplannerapp.presentation.ui.main.contract.MainDeps
import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.timeplannerapp.presentation.ui.main.contract.MainEvent
import com.example.timeplannerapp.presentation.ui.main.contract.MainViewState
import com.example.utils.manager.CoroutineManager
import com.example.utils.platform.communications.state.EffectCommunicator
import com.example.utils.platform.screenmodel.BaseViewModel
import com.example.utils.platform.screenmodel.work.WorkScope

class MainViewmodel(
     mainStateCommunicator: MainStateCommunicator,
    coroutineManager: CoroutineManager

) : BaseViewModel<MainViewState, MainEvent, MainAction, MainEffect, MainDeps>(
    stateCommunicator = mainStateCommunicator,
    effectCommunicator = EffectCommunicator.Empty(),
    coroutineManager = coroutineManager
){
    override suspend fun reduce(
        action: MainAction,
        currentState: MainViewState
    ): MainViewState {

    }

    override suspend fun WorkScope<MainViewState, MainAction, MainEffect>.handleEvent(
        event: MainEvent
    ) {
        when(event){
            is MainEvent.Init -> {

            }
            MainEvent.NavigateToEditor -> {

            }
        }

    }

    override fun init(deps: MainDeps) {
        if(!isInitialize.get()){
            super.init(deps)
            dispatchEvent(MainEvent.Init(deps.screenTarget))
        }
    }
}