package com.example.impl.presentation.models.schedules

import com.example.domain.entities.schedules.TimeTaskStatus
import java.util.Date

data class TimeTaskUi(
    val key : Long = 0L,
    val executionStatus : TimeTaskStatus = TimeTaskStatus.PLANNED,
    val date : Date,
    val endTime: Date,
    val createAt : Date? = null,
    val duration : Long,

)