package com.example.impl.domain.interactors

import com.example.domain.entities.schedules.Schedule
import com.example.impl.domain.entities.HomeFailures
import com.example.utils.functional.DomainResult
import com.example.utils.functional.FlowDomainResult
import java.util.Date

interface  ScheduleInteractor {
    suspend fun fetchOverviewSchedules(): DomainResult<HomeFailures, List<Schedule>>
    suspend fun fetchScheduleByDate(date : Long) : FlowDomainResult<HomeFailures, Schedule>
    suspend fun createSchedule(requireDay : Date): DomainResult<HomeFailures, Unit>
    suspend fun updateSchedule(schedule : Schedule) : DomainResult<HomeFailures, Unit>
    suspend fun fetchFeatureScheduleDate():Date?
    fun setFeatureScheduleDate(date : Date?)

    class Base(
    ){

    }
}
