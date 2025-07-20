package com.example.impl.domain.interactors

import com.example.domain.entities.schedules.TimeTask
import com.example.domain.repository.ScheduleRepository
import com.example.domain.repository.TemplatesRepository
import com.example.domain.repository.TimeTaskRepository
import com.example.impl.domain.common.HomeEitherWrapper
import com.example.impl.domain.entities.HomeFailures
import com.example.impl.domain.entities.TimeTaskImportanceException
import com.example.utils.extensions.extractAllItem
import com.example.utils.extensions.isCurrentDay
import com.example.utils.extensions.shiftDay
import com.example.utils.extensions.shiftMinutes
import com.example.utils.functional.DomainResult
import com.example.utils.functional.TimeRange
import com.example.utils.functional.TimeShiftException
import kotlinx.coroutines.flow.first
import java.util.Date

interface TimeShiftInteractor {
    suspend fun shiftUpTimeTask(task : TimeTask,shiftValue: Int): DomainResult<HomeFailures,List<TimeTask>>
    suspend fun shiftDownTimeTask(task : TimeTask,shiftValue: Int): DomainResult<HomeFailures,TimeTask>

    class Base(
        private val scheduleRepository: ScheduleRepository,
        private val timeTaskRepository: TimeTaskRepository,
        private val templatesRepository: TemplatesRepository,
        private val eitherWrapper: HomeEitherWrapper
    ): TimeShiftInteractor{
        override suspend fun shiftUpTimeTask(
            task: TimeTask,
            shiftValue: Int
        ): DomainResult<HomeFailures, List<TimeTask>> = eitherWrapper.wrap{
            val timeRange = TimeRange(task.date.shiftDay(-1),task.date.shiftDay(1))
            val schedules = scheduleRepository.fetchSchedulesByRange(timeRange).first()
            val allTimeTasks = schedules.map { it.timeTasks }.extractAllItem().sortedBy { it.timeRange.from }
            val nextTimeTask = allTimeTasks.firstOrNull{it.timeRange.from >= task.timeRange.to}
            val nextTimeTaskTemplate = templatesRepository.fetchAllTemplate().first().find { template ->
                nextTimeTask?.let { template.equalsTemplate(it) }?:false
            }
            val nextTime = nextTimeTask?.timeRange
            val shiftTime = task.timeRange.to.shiftMinutes(shiftValue)
            return@wrap if(nextTime == null || nextTime.from.time - shiftTime.time >= shiftValue){
                when(shiftTime.isCurrentDay(task.timeRange.to)){
                    true -> listOf(task.copy(timeRange = task.timeRange.copy(to = shiftTime))).apply {
                        timeTaskRepository.updateTimeTaskList(this)
                    }
                    false -> throw TimeShiftException()
                }
            }else {
                when (nextTime.to.time - shiftTime.time > 0) {
                    true -> {
                        if (nextTimeTask.priority.isImportant() || nextTimeTaskTemplate?.checkDateIsRepeat(Date()) == true) {
                            throw TimeTaskImportanceException()
                        }
                        val shiftTask = task.copy(timeRange = task.timeRange.copy(to = shiftTime))
                        val nextShiftTask = nextTimeTask.copy(timeRange = nextTimeTask.timeRange.copy(from = shiftTime))
                        val updatedTasks = listOf(shiftTask, nextShiftTask)
                        timeTaskRepository.updateTimeTaskList(updatedTasks)
                        return@wrap updatedTasks
                    }
                    false -> throw TimeShiftException()
                }
            }

        }

        override suspend fun shiftDownTimeTask(
            task: TimeTask,
            shiftValue: Int
        ): DomainResult<HomeFailures, TimeTask> = eitherWrapper.wrap {
            val shiftTime = task.timeRange.to.shiftMinutes(-shiftValue)
            if (shiftTime.time - task.timeRange.from.time > 0) {
                val timeRanges = task.timeRange.copy(to = shiftTime)
                val shiftTask = task.copy(timeRange = timeRanges)
                timeTaskRepository.updateTimeTask(shiftTask)
                return@wrap shiftTask
            } else {
                throw TimeShiftException()
            }
        }
    }

}