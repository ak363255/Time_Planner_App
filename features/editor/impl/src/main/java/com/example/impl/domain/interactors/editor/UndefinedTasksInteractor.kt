package com.example.impl.domain.interactors.editor

import com.example.domain.entities.schedules.UndefinedTask
import com.example.domain.repository.UndefinedTasksRepository
import com.example.impl.domain.common.editor.EditorEitherWrapper
import com.example.impl.domain.entities.EditorFailures
import com.example.utils.functional.DomainResult
import com.example.utils.functional.UnitDomainResult
import kotlinx.coroutines.flow.first

interface UndefinedTasksInteractor {
    suspend fun fetchAllUndefinedTasks() : DomainResult<EditorFailures, List<UndefinedTask>>
    suspend fun deleteUndefinedTask(taskId : Long): UnitDomainResult<EditorFailures>
    class Base(
        private val undefinedTaskRepository : UndefinedTasksRepository,
        private val eitherWrapper : EditorEitherWrapper
    ): UndefinedTasksInteractor{
        override suspend fun fetchAllUndefinedTasks(): DomainResult<EditorFailures, List<UndefinedTask>> = eitherWrapper.wrap {
             undefinedTaskRepository.fetchUndefinedTasks().first()
        }


        override suspend fun deleteUndefinedTask(taskId: Long): UnitDomainResult<EditorFailures> = eitherWrapper.wrap{
           undefinedTaskRepository.removeUndefinedTask(taskId)
        }

    }
}