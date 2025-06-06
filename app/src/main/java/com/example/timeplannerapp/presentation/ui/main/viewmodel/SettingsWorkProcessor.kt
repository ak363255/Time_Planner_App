package com.example.timeplannerapp.presentation.ui.main.viewmodel

import com.example.timeplannerapp.domain.interactors.SettingsInteractor
import com.example.timeplannerapp.presentation.ui.main.contract.MainAction
import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.utils.functional.handle
import com.example.utils.platform.screenmodel.work.FlowWorkProcessor
import com.example.utils.platform.screenmodel.work.FlowWorkResult
import com.example.utils.platform.screenmodel.work.WorkCommand
import kotlinx.coroutines.flow.flow

interface SettingsWorkProcessor : FlowWorkProcessor<SettingsWorkCommand,MainAction,MainEffect>{

    class Base(
        private val settingsInteractor: SettingsInteractor
    ): SettingsWorkProcessor{
        override suspend fun work(command: SettingsWorkCommand): FlowWorkResult<MainAction, MainEffect> = when(command) {
            SettingsWorkCommand.LoadSettings -> loadSettingsWork()
        }
        private fun loadSettingsWork() : FlowWorkResult<MainAction, MainEffect> = flow {
            settingsInteractor.fetchSettings().collect{settingsEither ->
                settingsEither.handle(
                    onLeftAction = {},
                    onRightAction = {
                        val action = MainAction.ChangeSettings(
                            language = it.themeSettings.language
                        )
                    }
                )

            }
        }
    }
}


sealed class SettingsWorkCommand : WorkCommand{
    data object LoadSettings : SettingsWorkCommand()
}