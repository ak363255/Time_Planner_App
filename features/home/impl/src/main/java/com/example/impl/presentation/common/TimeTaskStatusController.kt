package com.example.impl.presentation.common

import com.example.domain.entities.schedules.TimeTask
import com.example.domain.entities.schedules.TimeTaskStatus
import com.example.impl.domain.common.TimeTaskStatusChecker
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.utils.managers.DateManager

interface TimeTaskStatusController {
    fun updateStatus(timeTask : TimeTaskUi): TimeTaskUi
    class Base(
        private val statusManager: TimeTaskStatusChecker,
        private val dateManager : DateManager
    ): TimeTaskStatusController{
        override fun updateStatus(timeTask: TimeTaskUi) = with(timeTask) {
            val currentTime = dateManager.fetchCurrentDate()
            when (val status = statusManager.fetchStatus(timeToTimeRange(), currentTime)) {
                TimeTaskStatus.COMPLETED -> copy(
                    executionStatus = status,
                    progress = 1f,
                    leftTime = 0,
                    isCompleted = !(executionStatus == TimeTaskStatus.COMPLETED && !isCompleted),
                )
                TimeTaskStatus.PLANNED -> copy(
                    executionStatus = status,
                    progress = 0f,
                    leftTime = -1,
                    isCompleted = true,
                )
                TimeTaskStatus.RUNNING -> copy(
                    executionStatus = status,
                    progress = dateManager.calculateProgress(startTime, endTime),
                    leftTime = dateManager.calculateLeftTime(endTime),
                    isCompleted = true,
                )
            }
        }


    }
}