package com.example.domain.common

import com.example.domain.entities.schedules.DailyScheduleStatus
import java.util.Date

interface ScheduleStatusChecker {
    fun fetchState(requiredDate: Date, currentDate: Date): DailyScheduleStatus
    class Base() : ScheduleStatusChecker {
        override fun fetchState(
            requiredDate: Date,
            currentDate: Date
        ): DailyScheduleStatus {
            return if (requiredDate.time > currentDate.time) DailyScheduleStatus.PLANNED
            else if (requiredDate.time < currentDate.time) DailyScheduleStatus.REALIZED
            else DailyScheduleStatus.ACCOMPLISHMENT
        }

    }
}