package com.example.timeplannerapp.di.modules.domain_module

import com.example.data.repository.ScheduleRepositoryImpl
import com.example.data.repository.TasksSettingsRepositoryImpl
import com.example.data.repository.TemplatesRepositoryImpl
import com.example.data.repository.ThemeSettingRepositoryImpl
import com.example.data.repository.TimeTaskRepositoryImpl
import com.example.domain.common.ScheduleStatusChecker
import com.example.domain.repository.ScheduleRepository
import com.example.domain.repository.TasksSettingsRepository
import com.example.domain.repository.TemplatesRepository
import com.example.domain.repository.ThemeSettingsRepository
import com.example.domain.repository.TimeTaskRepository
import com.example.timeplannerapp.domain.interactors.SettingsInteractor
import org.koin.dsl.module

object AppDomainModule{
    val appDomainDep = module {
        single<ThemeSettingsRepository> { ThemeSettingRepositoryImpl(localDataSource = get()) }
        single<TasksSettingsRepository> { TasksSettingsRepositoryImpl(localDataSource = get()) }

        single<SettingsInteractor> {
            SettingsInteractor.Base(
                themeRepository = get(),
                taskRepository = get(),
                eitherWrapper = get()
            )
        }
        single<ScheduleStatusChecker> {
            ScheduleStatusChecker.Base()
        }
        single<ScheduleRepository> {
            ScheduleRepositoryImpl(
                localDataSource = get(),
                mapperToDomain = get(),
            )
        }

        single<TemplatesRepository> {
            TemplatesRepositoryImpl(
                localDataSource = get()
            )
        }

        single<TimeTaskRepository>
        {
            TimeTaskRepositoryImpl(
                localDataSource = get()
            )
        }

    }

}