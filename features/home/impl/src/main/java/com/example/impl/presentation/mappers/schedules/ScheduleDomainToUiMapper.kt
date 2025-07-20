package com.example.impl.presentation.mappers.schedules

import com.example.domain.entities.schedules.Schedule
import com.example.impl.presentation.models.schedules.ScheduleUi
import com.example.utils.extensions.mapToDate
import com.example.utils.functional.Mapper
import com.example.utils.managers.DateManager

interface ScheduleDomainToUiMapper  : Mapper<Schedule, ScheduleUi>{
    class Base(
        private val timeTaskMapperToUi : TimeTaskDomainToUiMapper,
        private val dateManager : DateManager
    ): ScheduleDomainToUiMapper{
        override fun map(input: Schedule): ScheduleUi = ScheduleUi(
            date = input.date.mapToDate(),
            dateStatus = input.status,
            timeTasks = input.timeTasks.map { timeTaskMapperToUi.map(it,false) },
            progress = when(input.timeTasks.isEmpty()){
                true -> 0f
                false -> input.timeTasks.count { dateManager.fetchCurrentDate().time > it.timeRange.to.time && it.isCompleted }/
                        input.timeTasks.size.toFloat()
            },
        )

    }
}

fun ScheduleUi.mapToDomain() = Schedule(
    date = date.time,
    status = dateStatus,
    timeTasks = timeTasks.map { it.mapToDomain() }
)