package com.example.domain.repository

import com.example.domain.entities.schedules.Schedule
import com.example.utils.functional.TimeRange
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun createSchedules(schedule : List<Schedule>)
    suspend fun fetchSchedulesByRange(timeRange : TimeRange?): Flow<List<Schedule>>
    fun fetchScheduleByDate(date:Long):Flow<Schedule?>
    suspend fun updateSchedule(schedule : Schedule)
    suspend fun deleteAllSchedule(): List<Schedule>
}