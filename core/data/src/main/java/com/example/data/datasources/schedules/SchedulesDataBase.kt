package com.example.data.datasources.schedules

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.datasources.categories.MainCategoriesDao
import com.example.data.datasources.subcategories.SubCategoriesDao
import com.example.data.datasources.templates.TemplatesDao
import com.example.data.datasources.undefinedtasks.UndefinedTasksDao
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.categories.SubCategoryEntity
import com.example.data.models.schedules.DailyScheduleEntity
import com.example.data.models.tasks.TimeTaskEntity
import com.example.data.models.tasks.UndefinedTaskEntity
import com.example.data.models.template.RepeatTimeEntity
import com.example.data.models.template.TemplateEntity

@Database(
    version = 1,
    entities = [
        TemplateEntity::class,
        RepeatTimeEntity::class,
        DailyScheduleEntity::class,
        TimeTaskEntity::class,
        UndefinedTaskEntity::class,
        MainCategoryEntity::class,
        SubCategoryEntity::class,

    ],
    exportSchema = true,
    autoMigrations = []
)
abstract class SchedulesDataBase : RoomDatabase(){
    abstract fun fetchScheduleDao(): SchedulesDao
    abstract fun fetchTemplatesDao(): TemplatesDao
    abstract fun fetchMainCategoriesDao() : MainCategoriesDao
    abstract fun fetchSubCategoriesDao() : SubCategoriesDao
    abstract fun fetchUndefinedTaskDao() : UndefinedTasksDao

    companion object{
        const val NAME: String = "SchedulesDataBase.db"
        fun create(context: Context) = Room.databaseBuilder(
            context = context,
            klass = SchedulesDataBase::class.java,
            name = NAME,
        ).build()
    }
}