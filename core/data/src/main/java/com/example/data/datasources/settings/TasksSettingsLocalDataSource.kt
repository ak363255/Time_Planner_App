package com.example.data.datasources.settings

import com.example.data.models.settings.TasksSettingsEntity
import kotlinx.coroutines.flow.Flow

interface TasksSettingsLocalDataSource {
    fun fetchSettings(): Flow<TasksSettingsEntity>
    suspend fun updateSettings(settings: TasksSettingsEntity)

    class Base(
        private val settingsDao : TasksSettingsDao
    ): TasksSettingsLocalDataSource{
        override fun fetchSettings(): Flow<TasksSettingsEntity> {
            return settingsDao.fetchSettingsFlow()
        }

        override suspend fun updateSettings(settings: TasksSettingsEntity) {
           settingsDao.updateSettings(settings)
        }

    }
}