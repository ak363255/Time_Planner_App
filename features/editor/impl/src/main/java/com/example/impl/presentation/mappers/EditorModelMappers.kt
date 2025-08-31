package com.example.impl.presentation.mappers

import com.example.domain.entities.schedules.TaskNotifications
import com.example.impl.domain.entities.EditModel
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.impl.presentation.models.editmodel.EditParameters
import com.example.impl.presentation.models.editmodel.TaskNotificationUi
import com.example.utils.extensions.duration
import com.example.utils.functional.TimeRange


internal fun EditModel.mapToUi() = EditModelUi(
    key = key,
    date = date,
    timeRange = TimeRange(startTime,endTime),
    createdAt = createdAt,
    duration = duration(startTime,endTime),
    mainCategory = mainCategory.maptoUi(),
    subCategory = subCategory?.mapToUi(),
    parameters = EditParameters(
        priority = priority,
        isEnableNotification = isEnableNotification,
        taskNotifications = taskNotifications.mapToUi(),
        isConsiderInStatistics = isConsiderInStatistics
    ),
    isCompleted = isCompleted,
    repeatEnabled = repeatEnabled,
    templateId = templateId,
    undefinedTaskId = undefinedTaskId,
    repeatTimes = repeatTimes,
    note = note
)

internal fun TaskNotifications.mapToUi() = TaskNotificationUi(
    fifteenMinutesBefore = fifteenMinutesBefore,
    oneHoursBefore = oneHourBefore,
    threeHourBefore = threeHourBefore,
    oneDayBefore = oneDayBefore,
    oneWeekBefore = oneWeekBefore,
    beforeEnd = beforeEnd
)

internal fun EditModelUi.mapToDomain() = EditModel(
    key = key,
    date = date,
    startTime = timeRange.from,
    endTime = timeRange.to,
    createdAt = createdAt,
    mainCategory = mainCategory.mapToDomain(),
    subCategory = subCategory?.mapToDomain(),
    isCompleted = isCompleted,
    priority = parameters.priority,
    isEnableNotification = parameters.isEnableNotification,
    taskNotifications = parameters.taskNotifications.mapToDomain(),
    isConsiderInStatistics = parameters.isConsiderInStatistics,
    repeatEnabled = repeatEnabled,
    templateId = templateId,
    undefinedTaskId = undefinedTaskId,
    repeatTimes = repeatTimes,
    note = note
)

internal fun TaskNotificationUi.mapToDomain() = TaskNotifications(
    fifteenMinutesBefore = fifteenMinutesBefore,
    oneHourBefore = oneHoursBefore,
    threeHourBefore = threeHourBefore,
    oneDayBefore = oneDayBefore,
    oneWeekBefore = oneWeekBefore,
    beforeEnd = beforeEnd
)
