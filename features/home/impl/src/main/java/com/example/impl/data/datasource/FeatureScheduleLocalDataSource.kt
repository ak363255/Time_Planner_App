package com.example.impl.data.datasource

import java.util.Date

interface FeatureScheduleLocalDataSource {
    suspend fun fetchScheduleData() : Date?
    fun setScheduleDate(date : Date?)

    class Base() : FeatureScheduleLocalDataSource{
        private var scheduleDate : Date? = null
        override suspend fun fetchScheduleData(): Date? {
            return scheduleDate
        }

        override fun setScheduleDate(date: Date?) {
            this.scheduleDate = date
        }

    }
}