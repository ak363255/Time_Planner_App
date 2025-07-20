package com.example.timeplannerapp.presentation.mappers

import com.example.domain.entities.schedules.NotificationTimeType
import com.example.presentation.ui.theme.tokens.TimePlannerStrings

fun NotificationTimeType.mapToString(strings: TimePlannerStrings) = when (this) {
    NotificationTimeType.BEFORE_TASK -> strings.beforeTaskNotifyText
    NotificationTimeType.START_TASK -> strings.startTaskNotifyText
    NotificationTimeType.AFTER_TASK -> strings.afterTaskNotifyText
}