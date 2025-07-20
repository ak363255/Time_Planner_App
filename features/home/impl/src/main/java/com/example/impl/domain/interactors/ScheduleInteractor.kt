package com.example.impl.domain.interactors

import androidx.compose.runtime.mutableStateListOf
import com.example.domain.common.ScheduleStatusChecker
import com.example.domain.entities.Template.Template
import com.example.domain.entities.schedules.Schedule
import com.example.domain.entities.schedules.TimeTask
import com.example.domain.repository.ScheduleRepository
import com.example.domain.repository.TemplatesRepository
import com.example.impl.domain.common.HomeEitherWrapper
import com.example.impl.domain.common.convertToTimeTask
import com.example.impl.domain.entities.HomeFailures
import com.example.impl.domain.repository.FeatureScheduleRepository
import com.example.utils.extensions.daysToMillis
import com.example.utils.extensions.mapToDate
import com.example.utils.extensions.shiftDay
import com.example.utils.functional.Constants.Date.NEXT_REPEAT_LIMIT
import com.example.utils.functional.Constants.Date.OVERVIEW_NEXT_DAYS
import com.example.utils.functional.Constants.Date.OVERVIEW_PREVIOUS_DAYS
import com.example.utils.functional.DomainResult
import com.example.utils.functional.FlowDomainResult
import com.example.utils.managers.DateManager
import com.example.utils.managers.TimeOverlayManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.Date
import kotlin.text.compareTo
import kotlin.unaryMinus

interface  ScheduleInteractor {
    suspend fun fetchOverviewSchedules(): DomainResult<HomeFailures, List<Schedule>>
    suspend fun fetchScheduleByDate(date : Long) : FlowDomainResult<HomeFailures, Schedule?>
    suspend fun createSchedule(requireDay : Date): DomainResult<HomeFailures, Unit>
    suspend fun updateSchedule(schedule : Schedule) : DomainResult<HomeFailures, Unit>
    suspend fun fetchFeatureScheduleDate():Date?
    fun setFeatureScheduleDate(date : Date?)

    class Base(
        private val scheduleRepository: ScheduleRepository,
        private val featureScheduleRepository : FeatureScheduleRepository,
        private val templateRepository : TemplatesRepository,
        private val statusChecker : ScheduleStatusChecker,
        private val dateManager: DateManager,
        private val overlayManager : TimeOverlayManager,
        private val eitherWrapper : HomeEitherWrapper
    ): ScheduleInteractor{
        override suspend fun fetchOverviewSchedules() = eitherWrapper.wrap {
            val currentDate = dateManager.fetchBeginningCurrentDay()
            val startDate = currentDate.shiftDay(-OVERVIEW_PREVIOUS_DAYS)
            return@wrap mutableListOf<Schedule>().apply {
                for (shiftAmount in 0..OVERVIEW_NEXT_DAYS + OVERVIEW_PREVIOUS_DAYS) {
                    val date = startDate.shiftDay(shiftAmount).time
                    val schedule = scheduleRepository.fetchScheduleByDate(date).firstOrNull() ?: if (date >= currentDate.time) {
                        createRecurringSchedule(date.mapToDate(), currentDate)
                    } else {
                        null
                    }
                    add(schedule ?: Schedule(date = date, status = statusChecker.fetchState(date.mapToDate(), currentDate)))
                }
            }
        }

        override suspend fun fetchScheduleByDate(date: Long) = eitherWrapper.wrapFlow {
            val currentDate = dateManager.fetchBeginningCurrentDay()
            val limit = NEXT_REPEAT_LIMIT.daysToMillis()
            scheduleRepository.fetchScheduleByDate(date).map { schedule ->
                if (schedule != null) {
                    val timeTasks = schedule.overlayTimeTask + schedule.timeTasks
                    val sortedTasks = timeTasks.sortedBy { timeTask -> timeTask.timeRange.to }
                    schedule.copy(timeTasks = sortedTasks)
                } else if (date >= currentDate.time && date - currentDate.time <= limit) {
                    createRecurringSchedule(date.mapToDate(), currentDate)
                } else {
                    null
                }
            }
        }

        override suspend fun createSchedule(requireDay: Date): DomainResult<HomeFailures, Unit> = eitherWrapper.wrap{
            val currentDate = dateManager.fetchBeginningCurrentDay()
            val status = statusChecker.fetchState(requireDay,currentDate)
            val schedule = Schedule(date = requireDay.time,status = status)
            scheduleRepository.createSchedules(listOf(schedule))
        }

        override suspend fun updateSchedule(schedule: Schedule): DomainResult<HomeFailures, Unit> = eitherWrapper.wrap {
            scheduleRepository.updateSchedule(schedule)
        }

        override suspend fun fetchFeatureScheduleDate(): Date? {
            return featureScheduleRepository.fetchScheduleDate().apply {
                featureScheduleRepository.setScheduleDate(null)
            }
        }

        override fun setFeatureScheduleDate(date: Date?) {
            featureScheduleRepository.setScheduleDate(date)
        }
        private suspend fun createRecurringSchedule(target: Date, current: Date): Schedule? {
            val templates = foundPlannedTemplates(target).apply { if (this.isEmpty()) return null }
            val templatesTimeTasks = templates.map { it.convertToTimeTask(date = target, createdAt = target) }
            val correctTimeTasks = mutableListOf<TimeTask>().apply {
                templatesTimeTasks.sortedBy { it.timeRange.from }.forEach { timeTask ->
                    val allTimeRanges = map { it.timeRange }
                    val overlayResult = overlayManager.isOverlay(timeTask.timeRange, allTimeRanges)
                    if (!overlayResult.isOverlay) add(timeTask)
                }
            }

            val status = statusChecker.fetchState(target, current)
            return Schedule(date = target.time, status = status, timeTasks = correctTimeTasks).apply {
                scheduleRepository.createSchedules(listOf(this))
            }
        }

        private suspend fun foundPlannedTemplates(date: Date) = mutableListOf<Template>().apply {
            templateRepository.fetchAllTemplate().first().filter { template ->
                template.repeatEnabled
            }.forEach { template ->
                template.repeatTimes.forEach { repeatTime ->
                    if (repeatTime.checkDateIsRepeat(date)) add(template)
                }
            }
        }
    }
}
