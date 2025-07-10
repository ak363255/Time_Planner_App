package com.example.impl.domain.interactors

import com.example.impl.domain.entities.HomeFailures
import com.example.utils.functional.DomainResult

interface  ScheduleInteractor {
    suspend fun fetchOverviewSchedules(): DomainResult<HomeFailures, List<Schedul>>
}