package com.example.impl.presentation.ui.home.contract

import com.example.domain.entities.schedules.DailyScheduleStatus
import com.example.domain.entities.settings.CalendarButtonBehavior
import com.example.domain.entities.settings.ViewToggleStatus
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class HomeViewState(
    val currentDate: Date? = null,
    val dateStatus : DailyScheduleStatus? = null,
    val taskViewStatus : ViewToggleStatus = ViewToggleStatus.COMPACT,
    val calendarButtonBehavior: CalendarButtonBehavior = CalendarButtonBehavior.SET_CURRENT_DATE,


): BaseViewState
