package com.example.impl.presentation.models.editmodel

import android.os.Parcelable
import com.example.domain.entities.schedules.TaskPriority
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditParameters(
    val priority : TaskPriority = TaskPriority.STANDARD,
    val isEnableNotification : Boolean = true,
    val taskNotifications : TaskNotificationUi = TaskNotificationUi(),
    val isConsiderInStatistics : Boolean = true,
): Parcelable