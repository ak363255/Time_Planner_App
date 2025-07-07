package com.example.timeplannerapp.presentation.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.timeplannerapp.presentation.ui.main.contract.MainAction
import com.example.timeplannerapp.presentation.ui.main.contract.MainDeps
import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.timeplannerapp.presentation.ui.main.contract.MainEvent
import com.example.timeplannerapp.presentation.ui.main.contract.MainViewState
import com.example.utils.managers.CoroutineManager
import com.example.utils.platform.screenmodel.BaseViewModel
import com.example.utils.platform.screenmodel.work.BackgroundWorkKey
import com.example.utils.platform.screenmodel.work.WorkScope

class MainViewmodel(
    mainStateCommunicator: MainStateCommunicator,
    mainEffectCommunicator: MainEffectCommunicator,
    private val settingsWorkProcessor: SettingsWorkProcessor,
    coroutineManager: CoroutineManager
) : BaseViewModel<MainViewState, MainEvent, MainAction, MainEffect, MainDeps>(
    stateCommunicator = mainStateCommunicator,
    effectCommunicator = mainEffectCommunicator,
    coroutineManager = coroutineManager
) {
    override suspend fun reduce(
        action: MainAction,
        currentState: MainViewState
    ): MainViewState =
        when (action) {
            is MainAction.ChangeSettings -> currentState.copy(
                language = action.language,
                theme = action.theme,
                colors = action.colors,
                isEnableDynamicColors = action.enableDynamicColors,
                secureMode = action.secureMode
            )

            MainAction.Navigate -> currentState
        }


    override suspend fun WorkScope<MainViewState, MainAction, MainEffect>.handleEvent(
        event: MainEvent
    ) {
        when (event) {
            is MainEvent.Init -> {
                launchBackgroundWork(key = BackgroundKey.LOAD_SETTING) {
                    settingsWorkProcessor.work(SettingsWorkCommand.LoadSettings)
                        .collectAndHandleWork()
                }
            }

            MainEvent.NavigateToEditor -> {

            }

            MainEvent.NavigateToMain -> {
                sendEffect(MainEffect.NavigateToMain)
            }
        }

    }

    override fun init(deps: MainDeps) {
        if (!isInitialize.get()) {
            super.init(deps)
            dispatchEvent(MainEvent.Init(deps.screenTarget))
        }
    }

    enum class BackgroundKey : BackgroundWorkKey {
        LOAD_SETTING, NAVIGATE
    }

    class MainViewModelFactory(
        private val mainStateCommunicator: MainStateCommunicator,
        private val mainEffectCommunicator: MainEffectCommunicator,
        private val settingsWorkProcessor: SettingsWorkProcessor,
        private val coroutineManager: CoroutineManager

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewmodel(
                mainStateCommunicator = mainStateCommunicator,
                mainEffectCommunicator = mainEffectCommunicator,
                settingsWorkProcessor = settingsWorkProcessor,
                coroutineManager = coroutineManager
            ) as T
        }
    }
}