package com.example.data.repository

import com.example.data.mappers.settings.mapToData
import com.example.data.mappers.settings.mapToDomain
import com.example.domain.entities.settings.ThemeSettings
import com.example.domain.repository.ThemeSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeSettingRepositoryImpl(
    private val localDataSource: ThemeSettingsLocalDataSource
): ThemeSettingsRepository{
    override fun fetchSettingsFlow(): Flow<ThemeSettings> {
        return localDataSource.fetchSettingsFlow().map {it-> it.mapToDomain() }
    }

    override suspend fun fetchSettings(): ThemeSettings {
        return localDataSource.fetchSettings().mapToDomain()
    }

    override suspend fun updateSettings(themeSettings: ThemeSettings) {
        localDataSource.updateSettings(themeSettings.mapToData())
    }

}