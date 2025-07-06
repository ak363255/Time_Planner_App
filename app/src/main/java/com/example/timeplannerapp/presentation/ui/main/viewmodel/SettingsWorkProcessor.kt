package com.example.timeplannerapp.presentation.ui.main.viewmodel

import com.example.impl.presentation.mappers.mapToUi
import com.example.timeplannerapp.domain.interactors.SettingsInteractor
import com.example.timeplannerapp.presentation.ui.main.contract.MainAction
import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.utils.functional.handle
import com.example.utils.platform.screenmodel.work.ActionResult
import com.example.utils.platform.screenmodel.work.FlowWorkProcessor
import com.example.utils.platform.screenmodel.work.FlowWorkResult
import com.example.utils.platform.screenmodel.work.WorkCommand
import kotlinx.coroutines.flow.flow

interface SettingsWorkProcessor : FlowWorkProcessor<SettingsWorkCommand, MainAction, MainEffect>{

    class Base(
        private val settingsInteractor: SettingsInteractor
    ): SettingsWorkProcessor{
        override suspend fun work(command: SettingsWorkCommand): FlowWorkResult<MainAction, MainEffect> = when(command) {
            SettingsWorkCommand.LoadSettings -> loadSettingsWork()
        }
        private fun loadSettingsWork() : FlowWorkResult<MainAction, MainEffect> = flow {
            settingsInteractor.fetchSettings().collect{settingsEither ->
                settingsEither.handle(
                    onLeftAction = {it ->
                        error(RuntimeException("Error getting Theme Setting"))
                    },
                    onRightAction = {it->
                        val action = MainAction.ChangeSettings(
                            language = it.themeSettings.language.mapToUi(),
                            theme = it.themeSettings.themeColors.mapToUi(),
                            colors = it.themeSettings.colorsType.mapToUi(),
                            enableDynamicColors = it.themeSettings.isDynamicColorEnable,
                            secureMode = it.taskSettings.secureMode
                        )
                        emit(ActionResult(action))
                    }
                )

            }
        }
    }
}


sealed class SettingsWorkCommand : WorkCommand{
    data object LoadSettings : SettingsWorkCommand()
}