package com.example.impl.data.repositories

import com.example.impl.data.datasource.FeatureScheduleLocalDataSource
import com.example.impl.domain.repository.FeatureScheduleRepository
import java.util.Date

class FeatureScheduleRepositoryImpl(
    private val localDataSource : FeatureScheduleLocalDataSource
) : FeatureScheduleRepository{
    override suspend fun fetchScheduleDate(): Date? {
        return localDataSource.fetchScheduleData()
    }

    override fun setScheduleDate(date: Date?) {
        localDataSource.setScheduleDate(date)
    }
}