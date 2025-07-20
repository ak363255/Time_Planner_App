package com.example.data.models.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.schedules.DailyScheduleEntity

@Entity(
    tableName = "timeTasks",
    foreignKeys = [
        ForeignKey(
            entity = DailyScheduleEntity::class,
            parentColumns = arrayOf("date"),
            childColumns = arrayOf("daily_schedule_date"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MainCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("main_category_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeTaskEntity(
    @PrimaryKey(autoGenerate = false)val key : Long,
    @ColumnInfo("daily_schedule_date", index = true)val dailyScheduleDate: Long,
    @ColumnInfo("next_schedule_date", index = true, defaultValue = "null")val nextScheduleDate: Long?,
    @ColumnInfo("start_time") val startTime: Long,
    @ColumnInfo("end_time") val endTime: Long,
    @ColumnInfo("created_at") val createdAt: Long? = null,
    @ColumnInfo("main_category_id", index = true) val mainCategoryId: Int,
    @ColumnInfo("sub_category_id", index = true) val subCategoryId: Int?,
    @ColumnInfo("is_completed", defaultValue = "1") val isCompleted: Boolean,
    @ColumnInfo("is_important") val isImportantMax: Boolean,
    @ColumnInfo("is_medium_important", defaultValue = "0") val isImportantMedium: Boolean,
    @ColumnInfo("is_enable_notification") val isEnableNotification: Boolean,
    @ColumnInfo("fifteen_minutes_before_notify", defaultValue = "0") val fifteenMinutesBeforeNotify: Boolean = false,
    @ColumnInfo("one_hour_before_notify", defaultValue = "0") val oneHourBeforeNotify: Boolean = false,
    @ColumnInfo("three_hour_before_notify", defaultValue = "0") val threeHourBeforeNotify: Boolean = false,
    @ColumnInfo("one_day_before_notify", defaultValue = "0") val oneDayBeforeNotify: Boolean = false,
    @ColumnInfo("one_week_before_notify", defaultValue = "0") val oneWeekBeforeNotify: Boolean = false,
    @ColumnInfo("before_end_notify", defaultValue = "0") val beforeEndNotify: Boolean = false,
    @ColumnInfo("is_consider_in_statistics") val isConsiderInStatistics: Boolean,
    @ColumnInfo("note") val note: String? = null,
)