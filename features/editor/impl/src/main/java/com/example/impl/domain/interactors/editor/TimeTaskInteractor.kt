package com.example.impl.domain.interactors.editor

import com.example.domain.common.ScheduleStatusChecker
import com.example.domain.entities.schedules.Schedule
import com.example.domain.entities.schedules.TimeTask
import com.example.domain.repository.ScheduleRepository
import com.example.domain.repository.TimeTaskRepository
import com.example.impl.domain.common.editor.EditorEitherWrapper
import com.example.impl.domain.entities.EditorFailures
import com.example.utils.extensions.extractAllItem
import com.example.utils.extensions.generateUniqueKey
import com.example.utils.extensions.shiftDay
import com.example.utils.functional.DomainResult
import com.example.utils.functional.TimeRange
import com.example.utils.managers.DateManager
import com.example.utils.managers.TimeOverlayException
import com.example.utils.managers.TimeOverlayManager
import kotlinx.coroutines.flow.first

interface TimeTaskInteractor {
    suspend fun addTimeTask(timeTask : TimeTask): DomainResult<EditorFailures, Long>
    suspend fun updateTimeTask(timeTask : TimeTask): DomainResult<EditorFailures, Long>
    suspend fun deleteTimeTask(key : Long): DomainResult<EditorFailures, Unit>
    class Base(
        private val scheduleRepository: ScheduleRepository,
        private val timeTaskRepository: TimeTaskRepository,
        private val statusChecker : ScheduleStatusChecker,
        private val overlayManager: TimeOverlayManager,
        private val dateManager: DateManager,
        private val eitherWrapper : EditorEitherWrapper
    ): TimeTaskInteractor{
        override suspend fun addTimeTask(timeTask: TimeTask) = eitherWrapper.wrap {
           val timeRange = TimeRange(timeTask.date.shiftDay(-1),timeTask.date.shiftDay(1))
           val schedules = scheduleRepository.fetchSchedulesByRange(timeRange).first().let { schedules ->
              if(schedules.find { it.date == timeTask.date.time } == null){
                 val createSchedule = Schedule(
                     date = timeTask.date.time,
                     status = statusChecker.fetchState(timeTask.date,dateManager.fetchBeginningCurrentDay())
                 )
                  scheduleRepository.createSchedules(listOf(createSchedule))
                  listOf(createSchedule)
              }else{
                  schedules
              }
           }
            val allTimeTask = schedules.map { it.timeTasks }.extractAllItem()
            val key = generateUniqueKey()
            checkIsOverlay(allTimeTask.map { it.timeRange },timeTask.timeRange){
                timeTaskRepository.addTimeTasks(listOf(timeTask.copy(key = key)))
            }
           return@wrap key;
        }

        override suspend fun updateTimeTask(timeTask: TimeTask): DomainResult<EditorFailures, Long> = eitherWrapper.wrap{
            val timeRange = TimeRange(timeTask.date.shiftDay(-1),timeTask.date.shiftDay(1))
            val schedules = scheduleRepository.fetchSchedulesByRange(timeRange).first()
            val allTimeTask = schedules.map { it.timeTasks }.extractAllItem().toMutableList().apply {
                removeAll { it.key == timeTask.key }
            }
            checkIsOverlay(allTimeTask.map { it.timeRange },timeTask.timeRange){
                timeTaskRepository.updateTimeTask(timeTask)
            }
            return@wrap timeTask.key;
        }

        override suspend fun deleteTimeTask(key: Long): DomainResult<EditorFailures, Unit> = eitherWrapper.wrap{
            timeTaskRepository.deleteTimeTask(listOf(key))
        }

        private suspend fun checkIsOverlay(
            allRanges : List<TimeRange>,
            range : TimeRange,
            block : suspend  () ->Unit,
        ) = overlayManager.isOverlay(current = range, allTimeRanges = allRanges).let{result ->
            if(result.isOverlay){
                throw TimeOverlayException(startOverlay = result.leftTimeBorder, endOverlay = result.rightTimeBorder)
            }else{
                block()
            }
        }

    }

}