package com.example.impl.presentation.models.schedules

import android.os.Parcelable
import com.example.domain.entities.schedules.TaskPriority
import com.example.domain.entities.schedules.TimeTaskStatus
import com.example.impl.presentation.models.categories.MainCategoryUi
import com.example.impl.presentation.models.categories.SubCategoryUi
import com.example.utils.extensions.duration
import com.example.utils.functional.TimeRange
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
data class TimeTaskUi(
    val key: Long = 0L,
    val executionStatus: TimeTaskStatus = TimeTaskStatus.PLANNED,
    val date: Date,
    val startTime: Date,
    val endTime: Date,
    val createAt: Date? = null,
    val duration: Long = duration(startTime, endTime),
    val leftTime: Long = 0L,
    val progress: Float = 0f,
    val mainCategory: MainCategoryUi,
    val subCategory: SubCategoryUi? = null,
    val isCompleted: Boolean = true,
    val priority: TaskPriority = TaskPriority.STANDARD,
    val isEnableNotification: Boolean = true,
    val taskNotifications: TaskNotificationsUi = TaskNotificationsUi(),
    val isConsiderInStatistics: Boolean = true,
    val isTemplate: Boolean = false,
    val note: String? = null,

    ) : Parcelable {
    fun timeToTimeRange() = TimeRange(startTime, endTime)
}