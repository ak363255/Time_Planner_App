package com.example.data.repository

import com.example.data.datasources.schedules.SchedulesLocalDataSource
import com.example.data.mappers.schedules.ScheduleDataToDomainMapper
import com.example.data.mappers.schedules.mapToData
import com.example.data.models.tasks.TimeTaskEntity
import com.example.domain.entities.schedules.Schedule
import com.example.domain.repository.ScheduleRepository
import com.example.utils.functional.TimeRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduleRepositoryImpl(
    private val localDataSource : SchedulesLocalDataSource,
    private val mapperToDomain : ScheduleDataToDomainMapper
) : ScheduleRepository{
    override suspend fun createSchedules(schedule: List<Schedule>) {
       val dailySchedules = schedule.map { it.mapToData()}
        val timeTasks = mutableListOf<TimeTaskEntity>().apply {
            schedule.forEach { schedule->
                addAll(schedule.timeTasks.map { it.mapToData() })
            }
        }
        localDataSource.addSchedules(dailySchedules,timeTasks)
    }

    override suspend fun fetchSchedulesByRange(timeRange: TimeRange?): Flow<List<Schedule>> {
        return localDataSource.fetchScheduleByRange(timeRange).map { schedules ->
            schedules.map{mapperToDomain.map(it)}
        }
    }

    override fun fetchScheduleByDate(date: Long): Flow<Schedule?> {
        return localDataSource.fetchScheduleByDate(date).map { it?.map(mapperToDomain) }

    }

    override suspend fun updateSchedule(schedule: Schedule) {
        localDataSource.updateTimeTasks(schedule.timeTasks.map { it.mapToData() })
    }

    override suspend fun deleteAllSchedule(): List<Schedule> {
      return localDataSource.removeAllSchedules().map { mapperToDomain.map(it) }
    }
}