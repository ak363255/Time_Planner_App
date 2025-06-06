package com.example.domain.repository

import com.example.domain.entities.settings.Settings
import com.example.domain.entities.settings.ThemeSettings
import kotlinx.coroutines.flow.Flow

interface ThemeSettingsRepository {
    fun fetchSettingsFlow(): Flow<ThemeSettings>
    suspend fun fetchSettings(): ThemeSettings
    suspend fun updateSettings(themeSettings: ThemeSettings)
}