package com.example.data.datasources.undefinedtasks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.models.tasks.UndefinedTaskDetails
import com.example.data.models.tasks.UndefinedTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UndefinedTasksDao {

    @Query("SELECT * FROM undefinedTasks")
    @Transaction
    fun fetchAllUndefinedTasks(): Flow<List<UndefinedTaskDetails>>

    @Insert(entity = UndefinedTaskEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateUndefinedTasks(entity: List<UndefinedTaskEntity>)

    @Query("DELETE FROM undefinedTasks WHERE key = :key")
    suspend fun removeUndefinedTask(key: Long)

    @Query("DELETE FROM undefinedTasks")
    suspend fun removeAllUndefinedTasks()
}