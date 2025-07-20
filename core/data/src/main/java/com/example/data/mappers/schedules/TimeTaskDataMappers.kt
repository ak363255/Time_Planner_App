package com.example.data.mappers.schedules

import com.example.data.mappers.categories.mapToDomain
import com.example.data.models.tasks.TimeTaskDetails
import com.example.data.models.tasks.TimeTaskEntity
import com.example.domain.entities.schedules.TaskNotifications
import com.example.domain.entities.schedules.TaskPriority
import com.example.domain.entities.schedules.TimeTask
import com.example.utils.extensions.isCurrentDay
import com.example.utils.extensions.mapToDate
import com.example.utils.extensions.shiftDay
import com.example.utils.functional.TimeRange
import kotlin.text.category
import kotlin.to

fun TimeTaskDetails.mapToDomain() = TimeTask(
    key = timeTask.key,
    date = timeTask.dailyScheduleDate.mapToDate(),
    timeRange = TimeRange(timeTask.startTime.mapToDate(), timeTask.endTime.mapToDate()),
    createdAt = timeTask.createdAt?.mapToDate(),
    category = mainCategory.mainCategory.mapToDomain(),
    subCategory = subCategory?.mapToDomain(mainCategory.mainCategory.mapToDomain()),
    isCompleted = timeTask.isCompleted,
    priority = when {
        timeTask.isImportantMax -> TaskPriority.MAX
        timeTask.isImportantMedium -> TaskPriority.MEDIUM
        else -> TaskPriority.STANDARD
    },
    isEnableNotification = timeTask.isEnableNotification,
    taskNotification = TaskNotifications(
        fifteenMinutesBefore = timeTask.fifteenMinutesBeforeNotify,
        oneHourBefore = timeTask.oneHourBeforeNotify,
        threeHourBefore = timeTask.threeHourBeforeNotify,
        oneDayBefore = timeTask.oneDayBeforeNotify,
        oneWeekBefore = timeTask.oneWeekBeforeNotify,
        beforeEnd = timeTask.beforeEndNotify,
    ),
    isConsiderInStatistics = timeTask.isConsiderInStatistics,
    note = timeTask.note,
)

fun TimeTask.mapToData() = TimeTaskEntity(
    key = key,
    dailyScheduleDate = date.time,
    nextScheduleDate = if (timeRange.to.isCurrentDay(date)) null else date.shiftDay(1).time,
    startTime = timeRange.from.time,
    endTime = timeRange.to.time,
    createdAt = createdAt?.time,
    mainCategoryId = category.id,
    subCategoryId = subCategory?.id,
    isCompleted = isCompleted,
    isImportantMedium = priority == TaskPriority.MEDIUM,
    isImportantMax = priority == TaskPriority.MAX,
    isEnableNotification = isEnableNotification,
    fifteenMinutesBeforeNotify = taskNotification.fifteenMinutesBefore,
    oneHourBeforeNotify = taskNotification.oneHourBefore,
    threeHourBeforeNotify = taskNotification.threeHourBefore,
    oneWeekBeforeNotify = taskNotification.oneWeekBefore,
    oneDayBeforeNotify = taskNotification.oneDayBefore,
    beforeEndNotify = taskNotification.beforeEnd,
    isConsiderInStatistics = isConsiderInStatistics,
    note = note,
)