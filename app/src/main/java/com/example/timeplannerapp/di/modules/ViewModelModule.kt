package com.example.timeplannerapp.di.modules

import com.example.data.datasources.settings.TasksSettingsLocalDataSource
import com.example.data.repository.TasksSettingsRepositoryImpl
import com.example.data.repository.ThemeSettingRepositoryImpl
import com.example.data.repository.ThemeSettingsLocalDataSource
import com.example.domain.repository.TasksSettingsRepository
import com.example.domain.repository.ThemeSettingsRepository
import com.example.impl.domain.common.SettingsEitherWrapper
import com.example.impl.domain.common.SettingsErrorHandler
import com.example.timeplannerapp.domain.interactors.SettingsInteractor
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainEffectCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainStateCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainViewmodel
import com.example.timeplannerapp.presentation.ui.main.viewmodel.SettingsWorkProcessor
import com.example.timeplannerapp.presentation.ui.tabs.viewmodel.TabScreenViewModel
import com.example.timeplannerapp.presentation.ui.tabs.viewmodel.TabStateCommunicator
import com.example.utils.managers.CoroutineManager
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class ViewModelModule {

    companion object{
        val module = module {
            single<MainStateCommunicator> { MainStateCommunicator.Base() }
            single<MainEffectCommunicator> { MainEffectCommunicator.Base() }
            single<CoroutineManager> { CoroutineManager.Base() }

            single<ThemeSettingsLocalDataSource> { ThemeSettingsLocalDataSource.Base(settingsDao = get()) }
            single<TasksSettingsLocalDataSource> { TasksSettingsLocalDataSource.Base(settingsDao = get()) }
            single< ThemeSettingsRepository> { ThemeSettingRepositoryImpl(localDataSource = get()) }
            single< TasksSettingsRepository> { TasksSettingsRepositoryImpl(localDataSource = get()) }
            single< SettingsErrorHandler> { SettingsErrorHandler() }
            single< SettingsEitherWrapper> { SettingsEitherWrapper.Base(errorHandler = get()) }

            single<SettingsInteractor> { SettingsInteractor.Base(themeRepository = get(), taskRepository = get(), eitherWrapper = get()) }
            single<SettingsWorkProcessor> { SettingsWorkProcessor.Base(settingsInteractor = get()) }
           /* single<MainViewModelFactory> {
                MainViewModelFactory(mainStateCommunicator = get(), mainEffectCommunicator = get(), settingsWorkProcessor = get(), coroutineManager = get())
            }*/
            viewModelOf(::MainViewmodel)
            single<TabStateCommunicator> { TabStateCommunicator.Base() }
            viewModelOf(::TabScreenViewModel)
        }
    }
}