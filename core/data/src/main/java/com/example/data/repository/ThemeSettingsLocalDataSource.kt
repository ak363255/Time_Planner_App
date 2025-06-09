package com.example.data.repository

import com.example.data.datasources.settings.ThemeSettingsDao
import com.example.data.models.settings.ThemeSettingEntity
import kotlinx.coroutines.flow.Flow

interface ThemeSettingsLocalDataSource {
    fun fetchSettingsFlow() : Flow<ThemeSettingEntity>
    suspend fun fetchSettings() : ThemeSettingEntity
    suspend fun updateSettings(settings: ThemeSettingEntity)

    class Base(
        private val settingsDao: ThemeSettingsDao
    ): ThemeSettingsLocalDataSource{
        override fun fetchSettingsFlow(): Flow<ThemeSettingEntity> {
            return settingsDao.fetchSettingsFlow()
        }

        override suspend fun fetchSettings(): ThemeSettingEntity {
            return settingsDao.fetchSettings()
        }

        override suspend fun updateSettings(settings: ThemeSettingEntity) {
            settingsDao.updateSettings(settings)
        }

    }
}