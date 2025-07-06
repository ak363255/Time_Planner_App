package com.example.timeplannerapp.domain.interactors

import com.example.domain.entities.settings.Settings
import com.example.domain.repository.TasksSettingsRepository
import com.example.domain.repository.ThemeSettingsRepository
import com.example.impl.domain.common.SettingsEitherWrapper
import com.example.impl.domain.common.SettingsFailures
import com.example.timeplannerapp.domain.common.MainEitherWrapper
import com.example.timeplannerapp.domain.common.MainFailures
import com.example.utils.functional.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

interface SettingsInteractor {
    suspend fun fetchSettings() : Flow<Either<SettingsFailures,Settings>>
    class Base(
        private val themeRepository : ThemeSettingsRepository,
        private val taskRepository: TasksSettingsRepository,
        private val eitherWrapper: SettingsEitherWrapper
    ):SettingsInteractor{
        override suspend fun fetchSettings(): Flow<Either<SettingsFailures, Settings>> = eitherWrapper.wrap{
            themeRepository.fetchSettingsFlow().zip(taskRepository.fetchSettings()){theme,task ->
                Settings(themeSettings = theme, taskSettings = task)
            }
        }
    }
}