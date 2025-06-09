package com.example.data.mappers.settings

import com.example.data.models.settings.TasksSettingsEntity
import com.example.domain.entities.settings.CalendarButtonBehavior
import com.example.domain.entities.settings.TaskSettings
import com.example.domain.entities.settings.ViewToggleStatus
import com.example.utils.functional.TimePeriod

fun TaskSettings.mapToData() = TasksSettingsEntity(
    id = 0,
    taskViewStatus = taskViewStatus.toString(),
    taskAnalyticsRange = taskAnalyticsRange.toString(),
    calendarButtonBehavior = calendarButtonBehavior.toString(),
    secureMode = secureMode
)

fun TasksSettingsEntity.mapToDomain() = TaskSettings(
    taskViewStatus = ViewToggleStatus.valueOf(taskViewStatus),
    taskAnalyticsRange = TimePeriod.valueOf(taskAnalyticsRange),
    calendarButtonBehavior = CalendarButtonBehavior.valueOf(calendarButtonBehavior),
    secureMode = secureMode
)