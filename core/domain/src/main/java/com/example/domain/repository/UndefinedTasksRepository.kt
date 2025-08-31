package com.example.domain.repository

import com.example.domain.entities.schedules.UndefinedTask
import kotlinx.coroutines.flow.Flow

interface UndefinedTasksRepository {
    suspend fun addOrUpdateUndefinedTasks(tasks : List<UndefinedTask>)
    fun fetchUndefinedTasks() : Flow<List<UndefinedTask>>
    suspend fun removeUndefinedTask(key : Long)
    suspend fun removeAllUndefinedTask()
}