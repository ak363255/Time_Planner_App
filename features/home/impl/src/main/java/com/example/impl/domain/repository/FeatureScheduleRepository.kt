package com.example.impl.domain.repository

import java.util.Date

interface FeatureScheduleRepository {
    suspend fun fetchScheduleDate() : Date?
    fun setScheduleDate(date:Date?)
}