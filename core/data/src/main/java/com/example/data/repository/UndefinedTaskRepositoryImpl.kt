package com.example.data.repository

import com.example.data.datasources.undefinedtasks.UndefinedTasksLocalDataSource
import com.example.data.mappers.schedules.mapToData
import com.example.data.mappers.schedules.mapToDomain
import com.example.domain.entities.schedules.UndefinedTask
import com.example.domain.repository.UndefinedTasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UndefinedTaskRepositoryImpl(
    private val localDataSource : UndefinedTasksLocalDataSource
): UndefinedTasksRepository {
    override suspend fun addOrUpdateUndefinedTasks(tasks: List<UndefinedTask>) {
       localDataSource.addOrUpdateUndefinedTasks(tasks.map { it.mapToData() })
    }

    override fun fetchUndefinedTasks(): Flow<List<UndefinedTask>> {
        return localDataSource.fetchUndefinedTasks().map { undefinedTasks ->
            undefinedTasks.map { it.mapToDomain() }
        }
    }

    override suspend fun removeUndefinedTask(key: Long) {
        localDataSource.removeUndefinedTask(key)
    }

    override suspend fun removeAllUndefinedTask() {
        localDataSource.removeAllUndefinedTasks()
    }
}