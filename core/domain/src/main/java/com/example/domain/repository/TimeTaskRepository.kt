package com.example.domain.repository

import com.example.domain.entities.schedules.TimeTask
import java.util.Date

interface TimeTaskRepository {
    suspend fun addTimeTasks(timeTasks: List<TimeTask>)
    suspend fun fetchAllTimeTaskByDate(date : Date): List<TimeTask>
    suspend fun updateTimeTaskList(timeTaskList:List<TimeTask>)
    suspend fun updateTimeTask(timeTask: TimeTask)
    suspend fun deleteTimeTask(keys:List<Long>)
}