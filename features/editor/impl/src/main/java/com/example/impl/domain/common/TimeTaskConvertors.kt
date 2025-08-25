package com.example.impl.domain.common

import com.example.domain.entities.Template.Template
import com.example.domain.entities.schedules.TimeTask
import com.example.impl.domain.entities.EditModel
import com.example.utils.functional.TimeRange

internal fun TimeTask.convertToEditModel(template: Template?, undefinedTaskId: Long?) = EditModel(
    key = key,
    date = date,
    startTime = timeRange.from,
    endTime = timeRange.to,
    createdAt = createdAt,
    mainCategory = category,
    subCategory = subCategory,
    priority = priority,
    isCompleted = isCompleted,
    isEnableNotification = isEnableNotification,
    taskNotifications = taskNotification,
    isConsiderInStatistics = isConsiderInStatistics,
    repeatEnabled = template?.repeatEnabled ?: false,
    templateId = template?.templateId,
    undefinedTaskId = undefinedTaskId,
    repeatTimes = template?.repeatTimes ?: emptyList(),
    note = note,
)

internal fun EditModel.convertToTimeTask() = TimeTask(
    key = key,
    date = date,
    timeRange = TimeRange(startTime, endTime),
    createdAt = createdAt,
    category = mainCategory,
    subCategory = subCategory,
    isCompleted = isCompleted,
    priority = priority,
    isEnableNotification = isEnableNotification,
    taskNotification = taskNotifications,
    isConsiderInStatistics = isConsiderInStatistics,
    note = note,
)