package com.example.data.repository

import com.example.data.datasources.settings.TasksSettingsLocalDataSource
import com.example.data.mappers.settings.mapToData
import com.example.data.mappers.settings.mapToDomain
import com.example.domain.entities.settings.TaskSettings
import com.example.domain.repository.TasksSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksSettingsRepositoryImpl(
    private val localDataSource: TasksSettingsLocalDataSource
): TasksSettingsRepository {
    override fun fetchSettings(): Flow<TaskSettings> {
        return localDataSource.fetchSettings().map { it.mapToDomain() }
    }

    override suspend fun updateSettings(settings: TaskSettings) {
        localDataSource.updateSettings(settings.mapToData())
    }

}