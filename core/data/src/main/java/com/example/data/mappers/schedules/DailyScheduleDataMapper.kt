package com.example.data.mappers.schedules

import com.example.data.models.schedules.DailyScheduleEntity
import com.example.domain.entities.schedules.Schedule

fun Schedule.mapToData() = DailyScheduleEntity(date = date)