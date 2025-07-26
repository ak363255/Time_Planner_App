package com.example.timeplannerapp.di.modules.data_module

import com.example.data.datasources.schedules.SchedulesDataBase
import com.example.data.datasources.schedules.SchedulesLocalDataSource
import com.example.data.datasources.settings.TasksSettingsLocalDataSource
import com.example.data.datasources.templates.TemplatesLocalDataSource
import com.example.data.mappers.schedules.ScheduleDataToDomainMapper
import com.example.data.repository.ThemeSettingsLocalDataSource
import org.koin.dsl.module

object AppDataModule {
    val appDataDep = module {
        single<TemplatesLocalDataSource> {
            TemplatesLocalDataSource.Base(
                templatesDao = get<SchedulesDataBase>().fetchTemplatesDao()
            )
        }
        single<ScheduleDataToDomainMapper> {
            ScheduleDataToDomainMapper.Base(
                scheduleStatusChecker = get(),
                dateManager = get()
            )
        }
        single<SchedulesLocalDataSource> {
            SchedulesLocalDataSource.Base(scheduleDao = get())
        }

        single<ThemeSettingsLocalDataSource> { ThemeSettingsLocalDataSource.Base(settingsDao = get()) }
        single<TasksSettingsLocalDataSource> { TasksSettingsLocalDataSource.Base(settingsDao = get()) }
    }
}