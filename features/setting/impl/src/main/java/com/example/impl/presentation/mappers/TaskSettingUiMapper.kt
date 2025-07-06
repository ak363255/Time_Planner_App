package com.example.impl.presentation.mappers

import com.example.domain.entities.settings.TaskSettings
import com.example.impl.presentation.models.TaskSettingsUi


fun TaskSettings.mapToUi(): TaskSettingsUi = TaskSettingsUi(
    taskViewStatus = this.taskViewStatus,
    calendarButtonBehavior = this.calendarButtonBehavior,
    secureMode = this.secureMode
)

fun TaskSettingsUi.toDomain() : TaskSettings = TaskSettings(
    taskViewStatus = this.taskViewStatus,
    calendarButtonBehavior = this.calendarButtonBehavior,
    secureMode = this.secureMode
)