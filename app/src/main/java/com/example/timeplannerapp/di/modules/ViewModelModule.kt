package com.example.timeplannerapp.di.modules

import com.example.data.datasources.schedules.SchedulesDao
import com.example.data.datasources.schedules.SchedulesDataBase
import com.example.data.datasources.schedules.SchedulesLocalDataSource
import com.example.data.datasources.settings.TasksSettingsLocalDataSource
import com.example.data.datasources.templates.TemplatesLocalDataSource
import com.example.data.mappers.schedules.ScheduleDataToDomainMapper
import com.example.data.repository.ScheduleRepositoryImpl
import com.example.data.repository.TasksSettingsRepositoryImpl
import com.example.data.repository.TemplatesRepositoryImpl
import com.example.data.repository.ThemeSettingRepositoryImpl
import com.example.data.repository.ThemeSettingsLocalDataSource
import com.example.data.repository.TimeTaskRepositoryImpl
import com.example.domain.common.ScheduleStatusChecker
import com.example.domain.repository.ScheduleRepository
import com.example.domain.repository.TasksSettingsRepository
import com.example.domain.repository.TemplatesRepository
import com.example.domain.repository.ThemeSettingsRepository
import com.example.domain.repository.TimeTaskRepository
import com.example.impl.data.datasource.FeatureScheduleLocalDataSource
import com.example.impl.data.repositories.FeatureScheduleRepositoryImpl
import com.example.impl.domain.common.HomeEitherWrapper
import com.example.impl.domain.common.HomeErrorHandler
import com.example.impl.domain.common.SettingsEitherWrapper
import com.example.impl.domain.common.SettingsErrorHandler
import com.example.impl.domain.interactors.ScheduleInteractor
import com.example.impl.domain.interactors.TimeShiftInteractor
import com.example.impl.domain.repository.FeatureScheduleRepository
import com.example.impl.presentation.common.TimeTaskStatusController
import com.example.impl.presentation.mappers.schedules.ScheduleDomainToUiMapper
import com.example.impl.presentation.mappers.schedules.TimeTaskDomainToUiMapper
import com.example.impl.presentation.ui.home.screenModel.HomeScreenModel
import com.example.impl.presentation.ui.home.views.ScheduleWorkProcessor
import com.example.presentation.ui.notifications.AlarmReceiverProvider
import com.example.presentation.ui.notifications.TimeTaskAlarmManager
import com.example.timeplannerapp.domain.interactors.SettingsInteractor
import com.example.timeplannerapp.presentation.receiver.AlarmReceiverProviderImpl
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainEffectCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainStateCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainViewmodel
import com.example.timeplannerapp.presentation.ui.main.viewmodel.SettingsWorkProcessor
import com.example.timeplannerapp.presentation.ui.tabs.viewmodel.TabScreenViewModel
import com.example.timeplannerapp.presentation.ui.tabs.viewmodel.TabStateCommunicator
import com.example.utils.managers.CoroutineManager
import com.example.utils.managers.DateManager
import com.example.utils.managers.TimeOverlayManager
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

class ViewModelModule {

    companion object {
        val module = module {
            single<MainStateCommunicator> { MainStateCommunicator.Base() }
            single<MainEffectCommunicator> { MainEffectCommunicator.Base() }
            single<CoroutineManager> { CoroutineManager.Base() }

            single<ThemeSettingsLocalDataSource> { ThemeSettingsLocalDataSource.Base(settingsDao = get()) }
            single<TasksSettingsLocalDataSource> { TasksSettingsLocalDataSource.Base(settingsDao = get()) }
            single<ThemeSettingsRepository> { ThemeSettingRepositoryImpl(localDataSource = get()) }
            single<TasksSettingsRepository> { TasksSettingsRepositoryImpl(localDataSource = get()) }
            single<SettingsErrorHandler> { SettingsErrorHandler() }
            single<SettingsEitherWrapper> { SettingsEitherWrapper.Base(errorHandler = get()) }

            single<SettingsInteractor> {
                SettingsInteractor.Base(
                    themeRepository = get(),
                    taskRepository = get(),
                    eitherWrapper = get()
                )
            }
            single<SettingsWorkProcessor> { SettingsWorkProcessor.Base(settingsInteractor = get()) }
            single<TabStateCommunicator> { TabStateCommunicator.Base() }
            /* single<MainViewModelFactory> {
                 MainViewModelFactory(mainStateCommunicator = get(), mainEffectCommunicator = get(), settingsWorkProcessor = get(), coroutineManager = get())
             }*/

            /*
               Dependencies for Home ScreenModel
             */
            single<SchedulesDataBase> {
                SchedulesDataBase.create(get())
            }
            single<SchedulesDao> {
                get<SchedulesDataBase>().fetchScheduleDao()
            }
            single<SchedulesLocalDataSource> {
                SchedulesLocalDataSource.Base(scheduleDao = get())
            }
            single<ScheduleStatusChecker> {
                ScheduleStatusChecker.Base()
            }
            single<DateManager> {
                DateManager.Base()
            }
            single<ScheduleDataToDomainMapper> {
                ScheduleDataToDomainMapper.Base(
                    scheduleStatusChecker = get(),
                    dateManager = get()
                )
            }
            single<ScheduleRepository> {
                ScheduleRepositoryImpl(
                    localDataSource = get(),
                    mapperToDomain = get(),
                )
            }
            single<FeatureScheduleLocalDataSource> {
                FeatureScheduleLocalDataSource.Base()
            }

           single<FeatureScheduleRepository> {
               FeatureScheduleRepositoryImpl(
                   localDataSource = get()
               )
           }
            single<TemplatesLocalDataSource> {
                TemplatesLocalDataSource.Base(
                    templatesDao = get<SchedulesDataBase>().fetchTemplatesDao()
                )
            }
            single<TemplatesRepository> {
                TemplatesRepositoryImpl(
                    localDataSource = get()
                )
            }
            single<TimeOverlayManager> {
                TimeOverlayManager.Base()
            }
            single<HomeErrorHandler> {
                HomeErrorHandler.Base()
            }
            single<HomeEitherWrapper> {
                HomeEitherWrapper.Base(
                    errorHandler = get()
                )
            }
            single<ScheduleInteractor> {
                ScheduleInteractor.Base(
                    scheduleRepository = get(),
                    featureScheduleRepository = get(),
                    templateRepository = get(),
                    statusChecker = get(),
                    dateManager = get(),
                    overlayManager = get(),
                    eitherWrapper = get()

                )
            }

            single<TimeTaskRepository>
            {
                TimeTaskRepositoryImpl(
                    localDataSource = get()
                )
            }

            single<TimeShiftInteractor> {
                TimeShiftInteractor.Base(
                    scheduleRepository = get(),
                    timeTaskRepository = get(),
                    templatesRepository = get(),
                    eitherWrapper = get()
                )
            }

            single<TimeTaskDomainToUiMapper> {
                TimeTaskDomainToUiMapper.Base(
                    dateManager = get(),
                    statusManager = get()
                )
            }

            single<ScheduleDomainToUiMapper> {
                ScheduleDomainToUiMapper.Base(
                    timeTaskMapperToUi = get(),
                    dateManager = get()
                )
            }

            single<TimeTaskStatusController> {
                TimeTaskStatusController.Base(
                    statusManager = get(),
                    dateManager = get()
                )
            }

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

            single<ScheduleWorkProcessor> {
                ScheduleWorkProcessor.Base(
                    scheduleInteractor = get(),
                    timeShiftInteractor = get(),
                    settingsInteractor = get(),
                    mapperToUi = get(),
                    statusController = get(),
                    dateManager = get(),
                    timeTaskAlarmManager = get()
                )
            }
            viewModelOf(::MainViewmodel)
            viewModelOf(::TabScreenViewModel)
            viewModelOf(::HomeScreenModel)
        }
    }
}