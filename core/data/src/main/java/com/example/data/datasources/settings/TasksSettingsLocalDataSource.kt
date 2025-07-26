package com.example.data.datasources.settings

import com.example.data.models.settings.TasksSettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface TasksSettingsLocalDataSource {
    fun fetchSettings(): Flow<TasksSettingsEntity>
    suspend fun updateSettings(settings: TasksSettingsEntity)

    class Base(
        private val settingsDao : TasksSettingsDao
    ): TasksSettingsLocalDataSource{
        override fun fetchSettings(): Flow<TasksSettingsEntity> {
            try {
                val res =  settingsDao.fetchSettingsFlow()
                return res
            }catch (e: Exception){
                return flowOf()
            }
        }

        override suspend fun updateSettings(settings: TasksSettingsEntity) {
           settingsDao.updateSettings(settings)
        }

    }
}