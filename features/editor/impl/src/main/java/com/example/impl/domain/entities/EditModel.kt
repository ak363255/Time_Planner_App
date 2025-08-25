package com.example.impl.domain.entities

import com.example.domain.entities.Template.RepeatTime
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.domain.entities.schedules.TaskNotifications
import com.example.domain.entities.schedules.TaskPriority
import java.util.Date

data class EditModel(
    val key: Long = 0L,
    val date: Date,
    val startTime: Date,
    val endTime: Date,
    val createdAt: Date? = null,
    val mainCategory: MainCategory = MainCategory(),
    val subCategory: SubCategory? = null,
    val isCompleted: Boolean = true,
    val priority: TaskPriority = TaskPriority.STANDARD,
    val isEnableNotification: Boolean = true,
    val isConsiderInStatistics: Boolean = true,
    val taskNotifications: TaskNotifications = TaskNotifications(),
    val repeatEnabled: Boolean = false,
    val templateId: Int? = null,
    val undefinedTaskId: Long? = null,
    val repeatTimes: List<RepeatTime> = emptyList(),
    val note: String? = null,
)
