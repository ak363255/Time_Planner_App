package com.example.domain.entities.settings

import com.example.utils.functional.TimePeriod

/**
 * Created by Amit on 06-06-2025.
 */
data class TaskSettings(
    val taskViewStatus : ViewToggleStatus = ViewToggleStatus.COMPACT,
    val taskAnalyticsRange : TimePeriod = TimePeriod.WEEK,
    val calendarButtonBehavior: CalendarButtonBehavior = CalendarButtonBehavior.SET_CURRENT_DATE,
    val secureMode : Boolean = false

)