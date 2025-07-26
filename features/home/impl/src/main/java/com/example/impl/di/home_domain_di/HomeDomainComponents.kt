package com.example.impl.di.home_domain_di

import com.example.impl.domain.common.HomeEitherWrapper
import com.example.impl.domain.common.HomeErrorHandler
import com.example.impl.domain.common.TimeTaskStatusChecker
import com.example.impl.domain.interactors.ScheduleInteractor
import com.example.impl.domain.interactors.TimeShiftInteractor
import org.koin.dsl.module

internal object HomeDomainComponents {
    val homeFeatureDomainDi = module {
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

        single<TimeShiftInteractor> {
            TimeShiftInteractor.Base(
                scheduleRepository = get(),
                timeTaskRepository = get(),
                templatesRepository = get(),
                eitherWrapper = get()
            )
        }

        single<TimeTaskStatusChecker> {
            TimeTaskStatusChecker.Base()
        }


        single<HomeErrorHandler> {
            HomeErrorHandler.Base()
        }
        single<HomeEitherWrapper> {
            HomeEitherWrapper.Base(
                errorHandler = get()
            )
        }
        single<com.example.impl.domain.interactors.SettingsInteractor> {
            com.example.impl.domain.interactors.SettingsInteractor.Base(
                tasksSettingsRepository = get(),
                eitherWrapper = get()
            )
        }
    }
}