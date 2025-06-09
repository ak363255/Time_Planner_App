package com.example.data.datasources.settings

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.data.models.settings.ThemeSettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeSettingsDao {

    @Query("SELECT * FROM ThemeSettings WHERE id = 0")
    fun fetchSettingsFlow(): Flow<ThemeSettingEntity>

    @Query("SELECT * FROM ThemeSettings WHERE id = 0")
    suspend fun fetchSettings(): ThemeSettingEntity

    @Update
    suspend fun updateSettings(entity: ThemeSettingEntity)
}