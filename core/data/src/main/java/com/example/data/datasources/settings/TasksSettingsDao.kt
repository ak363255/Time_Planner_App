package com.example.data.datasources.settings

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.data.models.settings.TasksSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksSettingsDao {
    @Query("SELECT * FROM TaskSettings WHERE id = 0")
    fun fetchSettingsFlow(): Flow<TasksSettingsEntity>

    @Update
    suspend fun updateSettings(entity: TasksSettingsEntity)
}