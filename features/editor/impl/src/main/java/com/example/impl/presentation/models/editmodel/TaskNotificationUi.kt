package com.example.impl.presentation.models.editmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskNotificationUi(
    val fifteenMinutesBefore : Boolean = false,
    val oneHoursBefore : Boolean = false,
    val threeHourBefore : Boolean = false,
    val oneDayBefore : Boolean = false,
    val oneWeekBefore : Boolean = false,
    val beforeEnd : Boolean = false
): Parcelable
