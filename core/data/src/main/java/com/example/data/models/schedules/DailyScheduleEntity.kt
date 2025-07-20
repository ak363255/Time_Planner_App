package com.example.data.models.schedules

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailySchedules")
data class DailyScheduleEntity(
    @PrimaryKey val date : Long
)
