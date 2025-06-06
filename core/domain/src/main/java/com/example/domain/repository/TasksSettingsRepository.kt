package com.example.domain.repository

import com.example.domain.entities.settings.TaskSettings
import kotlinx.coroutines.flow.Flow

/**
 * Created by Amit on 06-06-2025.
 */
interface TasksSettingsRepository {
    fun fetchSettings() : Flow<TaskSettings>
    suspend fun updateSettings(settings : TaskSettings)
}