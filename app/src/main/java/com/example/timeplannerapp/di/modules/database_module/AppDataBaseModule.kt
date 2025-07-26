package com.example.timeplannerapp.di.modules.database_module

import com.example.data.datasources.schedules.SchedulesDao
import com.example.data.datasources.schedules.SchedulesDataBase
import com.example.data.datasources.settings.SettingsDataBase
import com.example.data.datasources.settings.TasksSettingsDao
import com.example.data.datasources.settings.ThemeSettingsDao
import org.koin.dsl.module

class AppDataBaseModule {
    companion object {
        val appDatabaseDep = module {
            single<SettingsDataBase> {
                SettingsDataBase.Companion.create(get())
            }
            single<SchedulesDataBase> {
                SchedulesDataBase.create(get())
            }
            single<TasksSettingsDao> {
                get<SettingsDataBase>().fetchTasksSettingsDao()
            }

            single<ThemeSettingsDao> {
                get<SettingsDataBase>().fetchThemeSettingsDao()
            }
            single<SchedulesDao> {
                get<SchedulesDataBase>().fetchScheduleDao()
            }

        }
    }
}