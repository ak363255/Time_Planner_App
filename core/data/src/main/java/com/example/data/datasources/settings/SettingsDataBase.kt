package com.example.data.datasources.settings

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.models.settings.TasksSettingsEntity
import com.example.data.models.settings.ThemeSettingEntity

@Database(
    version = 1,
    entities = [ThemeSettingEntity::class, TasksSettingsEntity::class],
    exportSchema = true
)
abstract class SettingsDataBase : RoomDatabase(){
    abstract fun fetchThemeSettingsDao() : ThemeSettingsDao
    abstract fun fetchTasksSettingsDao() : TasksSettingsDao

    companion object{
        const val NAME = "SettingsDataBase.db"
        fun create(context: Context) = Room.databaseBuilder(
            context = context,
            klass = SettingsDataBase::class.java,
            name = NAME,
        ).createFromAsset("database/settings_prepopulated.db").build()

    }
}