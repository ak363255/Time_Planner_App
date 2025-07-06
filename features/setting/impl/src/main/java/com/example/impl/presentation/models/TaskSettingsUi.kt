package com.example.impl.presentation.models

import android.os.Parcelable
import com.example.domain.entities.settings.CalendarButtonBehavior
import com.example.domain.entities.settings.ViewToggleStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskSettingsUi(
    val taskViewStatus : ViewToggleStatus = ViewToggleStatus.COMPACT,
    val calendarButtonBehavior: CalendarButtonBehavior = CalendarButtonBehavior.SET_CURRENT_DATE,
     val secureMode : Boolean = false
): Parcelable