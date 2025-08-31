package com.example.data.datasources.undefinedtasks

import com.example.data.models.tasks.UndefinedTaskDetails
import com.example.data.models.tasks.UndefinedTaskEntity
import kotlinx.coroutines.flow.Flow

interface UndefinedTasksLocalDataSource {
    suspend fun addOrUpdateUndefinedTasks(tasks : List<UndefinedTaskEntity>)
    fun fetchUndefinedTasks(): Flow<List<UndefinedTaskDetails>>
    suspend fun removeUndefinedTask(key:Long)
    suspend fun removeAllUndefinedTasks()

    class Base(
        private val undefinedTasksDao : UndefinedTasksDao
    ): UndefinedTasksLocalDataSource{
        override suspend fun addOrUpdateUndefinedTasks(tasks: List<UndefinedTaskEntity>) {
            undefinedTasksDao.addOrUpdateUndefinedTasks(tasks)
        }

        override fun fetchUndefinedTasks(): Flow<List<UndefinedTaskDetails>> {
            return undefinedTasksDao.fetchAllUndefinedTasks()
        }

        override suspend fun removeUndefinedTask(key: Long) {
            undefinedTasksDao.removeUndefinedTask(key)
        }

        override suspend fun removeAllUndefinedTasks() {
            undefinedTasksDao.removeAllUndefinedTasks()
        }

    }
}