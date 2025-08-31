package com.example.timeplannerapp.di.modules.presentation_module

import com.example.impl.presentation.ui.home.screenModel.HomeEffectCommunicator
import com.example.presentation.ui.notifications.AlarmReceiverProvider
import com.example.presentation.ui.notifications.TimeTaskAlarmManager
import com.example.timeplannerapp.presentation.receiver.AlarmReceiverProviderImpl
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainEffectCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainStateCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainViewmodel
import com.example.timeplannerapp.presentation.ui.main.viewmodel.SettingsWorkProcessor
import com.example.timeplannerapp.presentation.ui.tabs.viewmodel.TabScreenViewModel
import com.example.timeplannerapp.presentation.ui.tabs.viewmodel.TabStateCommunicator
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

object AppPresentationModule {
    val appUiDep = module {
            single<MainStateCommunicator> { MainStateCommunicator.Base() }
            single<MainEffectCommunicator> { MainEffectCommunicator.Base() }

            single<SettingsWorkProcessor> { SettingsWorkProcessor.Base(settingsInteractor = get()) }
            single<TabStateCommunicator> { TabStateCommunicator.Base() }

            single<AlarmReceiverProvider> {
                AlarmReceiverProviderImpl(
                    context = get()
                )
            }

            single<TimeTaskAlarmManager> {
                TimeTaskAlarmManager.Base(
                    context = get(),
                    receiverProvider = get(),
                    dateManager = get()
                )
            }

            single<HomeEffectCommunicator> {
                HomeEffectCommunicator.Base()
            }
            viewModelOf(::MainViewmodel)
            viewModelOf(::TabScreenViewModel)
    }
}