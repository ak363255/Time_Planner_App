package com.example.domain.entities.schedules

import com.example.utils.functional.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

data class TimeTask(
    val key : Long = 0L,
    @Serializable(DateSerializer::class)
    val date : Date,
    @Serializable(DateSerializer::class)
    val createdAt : Date? = null,
    val timeRange : TimeRa
)