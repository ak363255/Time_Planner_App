package com.example.data.mappers.schedules

import com.example.data.models.schedules.DailyScheduleEntity
import com.example.data.models.schedules.ScheduleDetails
import com.example.domain.common.ScheduleStatusChecker
import com.example.domain.entities.schedules.Schedule
import com.example.utils.extensions.mapToDate
import com.example.utils.functional.Mapper
import com.example.utils.managers.DateManager

interface ScheduleDataToDomainMapper : Mapper<ScheduleDetails, Schedule>{
    class Base(
        private val scheduleStatusChecker: ScheduleStatusChecker,
        private val dateManager : DateManager
    ): ScheduleDataToDomainMapper{
        override fun map(input: ScheduleDetails): Schedule = Schedule(
            date = input.dailySchedule.date,
            status = scheduleStatusChecker.fetchState(
                requiredDate = input.dailySchedule.date.mapToDate(),
                currentDate = dateManager.fetchEndCurrentDay()
            ),
            timeTasks = input.timeTasks.map { timeTaskDetails -> timeTaskDetails.mapToDomain() },
            overlayTimeTask = input.overlayTimeTasks.map { timeTaskDetails ->
                timeTaskDetails.mapToDomain()
            }
        )

    }
}