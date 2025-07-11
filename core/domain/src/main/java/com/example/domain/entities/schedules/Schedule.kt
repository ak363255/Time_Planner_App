package com.example.domain.entities.schedules

import com.example.utils.functional.Mapper

data class Schedule(
    val date : Long,
    val status : DailyScheduleStatus,
    val timeTasks : List<TimeTask> = emptyList(),
    val overlayTimeTask : List<TimeTask> = emptyList()
){
    fun <T> map(mapper : Mapper<Schedule,T>) = mapper.map(this)
}

fun List<Schedule>.fetchAllTimeTasks() = map { it.overlayTimeTask + it.timeTasks }