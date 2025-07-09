package com.example.impl.presentation.models.schedules

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskNotificationsUi(
    val fifteenMinutesBefore: Boolean = false,
    val oneHourBefore: Boolean = false,
    val threeHourBefore: Boolean = false,
    val oneDayBefore: Boolean = false,
    val oneWeekBefore: Boolean = false,
    val beforeEnd: Boolean = false,
): Parcelable