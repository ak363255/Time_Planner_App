package com.example.data.datasources.schedules

import com.example.data.models.schedules.DailyScheduleEntity
import com.example.data.models.schedules.ScheduleDetails
import com.example.data.models.tasks.TimeTaskEntity
import com.example.domain.entities.schedules.DailyScheduleStatus
import com.example.domain.entities.schedules.TimeTask
import com.example.utils.functional.TimeRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface SchedulesLocalDataSource {
    suspend fun addSchedules(schedules : List<DailyScheduleEntity>,timeTasks : List<TimeTaskEntity>)
    suspend fun addTimeTasks(tasks : List<TimeTaskEntity>)
    fun fetchScheduleByDate(date : Long): Flow<ScheduleDetails?>
    suspend fun fetchScheduleByRange(timeRange : TimeRange?):Flow<List<ScheduleDetails>>
    suspend fun updateTimeTasks(timeTasks:List<TimeTaskEntity>)
    suspend fun removeDailySchedule(schedule : DailyScheduleEntity)
    suspend fun removeAllSchedules():List<ScheduleDetails>
    suspend fun removeTimeTasksByKey(keys:List<Long>)

    class Base(
        private val scheduleDao: SchedulesDao
    ): SchedulesLocalDataSource{
        override suspend fun addSchedules(
            schedules: List<DailyScheduleEntity>,
            timeTasks: List<TimeTaskEntity>
        ) {
            scheduleDao.addDailySchedules(schedules)
            scheduleDao.addTimeTasks(timeTasks)
        }

        override suspend fun addTimeTasks(tasks: List<TimeTaskEntity>) {
            scheduleDao.addTimeTasks(tasks)
        }

        override fun fetchScheduleByDate(date: Long): Flow<ScheduleDetails?> {
            return scheduleDao.fetchDailyScheduleByDate(date)
        }

        override suspend fun fetchScheduleByRange(timeRange: TimeRange?): Flow<List<ScheduleDetails>> {
           return if(timeRange != null){
               scheduleDao.fetchDailySchedulesByRange(start = timeRange.from.time, end = timeRange.to.time)
           }else{
               scheduleDao.fetchAllSchedules()
           }
        }

        override suspend fun updateTimeTasks(timeTasks: List<TimeTaskEntity>) {
            scheduleDao.updateTimeTasks(timeTasks)
        }

        override suspend fun removeDailySchedule(schedule: DailyScheduleEntity) {
          scheduleDao.removeDailySchedule(schedule)
        }

        override suspend fun removeAllSchedules(): List<ScheduleDetails> {
            val deletableSchedules = scheduleDao.fetchAllSchedules().first()
            scheduleDao.removeAllSchedules()
            return deletableSchedules
        }

        override suspend fun removeTimeTasksByKey(keys: List<Long>) {
          scheduleDao.removeTimeTasksByKey(keys)
        }

    }
}