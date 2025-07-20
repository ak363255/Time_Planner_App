package com.example.impl.domain.common

import com.example.domain.entities.schedules.TimeTaskStatus
import com.example.utils.functional.TimeRange
import java.util.Date

interface TimeTaskStatusChecker {
    fun fetchStatus(timeRange: TimeRange,currentDate : Date): TimeTaskStatus
    class Base  : TimeTaskStatusChecker {

        override fun fetchStatus(timeRange: TimeRange, currentDate: Date): TimeTaskStatus {
            return if (currentDate.time > timeRange.from.time && currentDate.time < timeRange.to.time) {
                TimeTaskStatus.RUNNING
            } else if (currentDate.time > timeRange.to.time) {
                TimeTaskStatus.COMPLETED
            } else {
                TimeTaskStatus.PLANNED
            }
        }
    }
}