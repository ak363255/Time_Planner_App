package com.example.timeplannerapp.di.modules

import com.example.data.datasources.settings.SettingsDataBase
import com.example.data.datasources.settings.TasksSettingsDao
import com.example.data.datasources.settings.ThemeSettingsDao
import org.koin.dsl.module

class DataBaseModule {
    companion object{
        val databaseModule = module {
            single<SettingsDataBase>{
                SettingsDataBase.create(get())
            }
            single<TasksSettingsDao> {
                get<SettingsDataBase>().fetchTasksSettingsDao()
            }

            single<ThemeSettingsDao> {
                get<SettingsDataBase>().fetchThemeSettingsDao()
            }
        }
    }
}