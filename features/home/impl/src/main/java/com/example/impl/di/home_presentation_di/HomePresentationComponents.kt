package com.example.impl.di.home_presentation_di

import com.example.impl.presentation.common.TimeTaskStatusController
import com.example.impl.presentation.mappers.schedules.ScheduleDomainToUiMapper
import com.example.impl.presentation.mappers.schedules.TimeTaskDomainToUiMapper
import com.example.impl.presentation.ui.home.screenModel.HomeEffectCommunicator
import com.example.impl.presentation.ui.home.screenModel.HomeScreenModel
import com.example.impl.presentation.ui.home.screenModel.HomeStateCommunicator
import com.example.impl.presentation.ui.home.screenModel.NavigationWorkProcessor
import com.example.impl.presentation.ui.home.screenModel.ScheduleWorkProcessor
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal object HomePresentationComponents{
    val homeFeaturePresentationDi = module {
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
        single<NavigationWorkProcessor> {
            NavigationWorkProcessor.Base(templateInteractor = get())
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
        single<HomeStateCommunicator> {
            HomeStateCommunicator.Base()
        }
        single<HomeEffectCommunicator> {
            HomeEffectCommunicator.Base()
        }
        viewModelOf(::HomeScreenModel)
    }
}