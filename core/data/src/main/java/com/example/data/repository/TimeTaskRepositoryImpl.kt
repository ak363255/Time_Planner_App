package com.example.data.repository

import com.example.data.datasources.schedules.SchedulesLocalDataSource
import com.example.data.mappers.schedules.mapToData
import com.example.data.mappers.schedules.mapToDomain
import com.example.domain.entities.schedules.TimeTask
import com.example.domain.repository.TimeTaskRepository
import kotlinx.coroutines.flow.first
import java.util.Date

class TimeTaskRepositoryImpl(
    private val localDataSource : SchedulesLocalDataSource
) : TimeTaskRepository{
    override suspend fun addTimeTasks(timeTasks: List<TimeTask>) {
        localDataSource.addTimeTasks(timeTasks.map { it.mapToData() })
    }

    override suspend fun fetchAllTimeTaskByDate(date: Date): List<TimeTask> {
        val schedule = localDataSource.fetchScheduleByDate(date.time).first()?:return emptyList()
        val timeTasks = schedule.overlayTimeTasks + schedule.timeTasks
        return timeTasks.map { timeTaskDetails -> timeTaskDetails.mapToDomain() }
    }

    override suspend fun updateTimeTaskList(timeTaskList: List<TimeTask>) {
        localDataSource.updateTimeTasks(timeTaskList.map { it.mapToData() })
    }

    override suspend fun updateTimeTask(timeTask: TimeTask) {
        localDataSource.updateTimeTasks(listOf(timeTask.mapToData()))
    }

    override suspend fun deleteTimeTask(keys: List<Long>) {
        localDataSource.removeTimeTasksByKey(keys)
    }
}