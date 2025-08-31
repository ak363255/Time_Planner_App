package com.example.impl.presentation.models.schedules

import android.os.Parcelable
import com.example.domain.entities.schedules.DailyScheduleStatus
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
 data class ScheduleUi(
    val date : Date,
    val dateStatus : DailyScheduleStatus,
    val timeTasks : List<TimeTaskUi>,
    val progress : Float
): Parcelable