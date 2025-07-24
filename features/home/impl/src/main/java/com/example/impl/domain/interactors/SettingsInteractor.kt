package com.example.impl.domain.interactors

import com.example.domain.entities.settings.TaskSettings
import com.example.domain.repository.TasksSettingsRepository
import com.example.impl.domain.common.HomeEitherWrapper
import com.example.impl.domain.entities.HomeFailures
import com.example.utils.functional.DomainResult
import com.example.utils.functional.UnitDomainResult
import kotlinx.coroutines.flow.Flow

interface SettingsInteractor {
    suspend fun fetchTaskSetting() : Flow<DomainResult<HomeFailures, TaskSettings>>
    suspend fun updateTasksSetting(settings : TaskSettings): UnitDomainResult<HomeFailures>
    class Base(
        private val tasksSettingsRepository: TasksSettingsRepository,
        private val eitherWrapper : HomeEitherWrapper

    ): SettingsInteractor{
        override suspend fun fetchTaskSetting(): Flow<DomainResult<HomeFailures, TaskSettings>> = eitherWrapper.wrapFlow {
            tasksSettingsRepository.fetchSettings()
        }

        override suspend fun updateTasksSetting(settings: TaskSettings): UnitDomainResult<HomeFailures>  = eitherWrapper.wrap{
            tasksSettingsRepository.updateSettings(settings)
        }

    }
}
