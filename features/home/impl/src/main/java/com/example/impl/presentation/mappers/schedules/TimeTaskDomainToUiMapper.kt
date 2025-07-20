package com.example.impl.presentation.mappers.schedules

import com.example.domain.entities.schedules.TaskNotifications
import com.example.domain.entities.schedules.TimeTask
import com.example.impl.domain.common.TimeTaskStatusChecker
import com.example.impl.presentation.mappers.categories.mapToDomain
import com.example.impl.presentation.mappers.categories.mapToUi
import com.example.impl.presentation.models.schedules.TaskNotificationsUi
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.utils.extensions.duration
import com.example.utils.functional.ParameterizedMapper
import com.example.utils.functional.TimeRange
import com.example.utils.managers.DateManager

interface TimeTaskDomainToUiMapper : ParameterizedMapper<TimeTask, TimeTaskUi, Boolean>{
    class Base (
        private val dateManager : DateManager,
        private val statusManager : TimeTaskStatusChecker
    ): TimeTaskDomainToUiMapper {
        override fun map(
            input: TimeTask,
            parameter: Boolean
        ): TimeTaskUi = TimeTaskUi(
            executionStatus = statusManager.fetchStatus(
                input.timeRange,
                dateManager.fetchCurrentDate()
            ),
            key = input.key,
            date = input.date,
            startTime = input.timeRange.from,
            endTime = input.timeRange.to,
            createAt = input.createdAt,
            duration = duration(input.timeRange),
            leftTime = dateManager.calculateLeftTime(input.timeRange.to),
            progress = dateManager.calculateProgress(input.timeRange.from, input.timeRange.to),
            mainCategory = input.category.mapToUi(),
            subCategory = input.subCategory?.mapToUi(),
            isCompleted = input.isCompleted,
            priority = input.priority,
            isEnableNotification = input.isEnableNotification,
            taskNotifications = input.taskNotification.mapToUi(),
            isConsiderInStatistics = input.isConsiderInStatistics,
            isTemplate = parameter,
            note = input.note
        )
    }
}

fun TaskNotifications.mapToUi() = TaskNotificationsUi(
    fifteenMinutesBefore = fifteenMinutesBefore,
    oneHourBefore = oneHourBefore,
    threeHourBefore = threeHourBefore,
    oneDayBefore = oneDayBefore,
    oneWeekBefore = oneWeekBefore,
    beforeEnd = beforeEnd,
)

fun TimeTaskUi.mapToDomain() = TimeTask(
    key = key,
    date = date,
    timeRange = TimeRange(startTime, endTime),
    createdAt = this.createAt,
    category = mainCategory.mapToDomain(),
    subCategory = subCategory?.mapToDomain(),
    priority = priority,
    isCompleted = isCompleted,
    isEnableNotification = isEnableNotification,
    taskNotification = taskNotifications.mapToDomain(),
    isConsiderInStatistics = isConsiderInStatistics,
    note = note,
)


fun TaskNotificationsUi.mapToDomain() = TaskNotifications(
    fifteenMinutesBefore = fifteenMinutesBefore,
    oneHourBefore = oneHourBefore,
    threeHourBefore = threeHourBefore,
    oneDayBefore = oneDayBefore,
    oneWeekBefore = oneWeekBefore,
    beforeEnd = beforeEnd,
)
