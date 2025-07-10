package com.example.domain.entities.schedules

data class Schedule(
    val date : Long,
    val status : DailyScheduleStatus,
    val timeTasks : List<TimeTask>
)