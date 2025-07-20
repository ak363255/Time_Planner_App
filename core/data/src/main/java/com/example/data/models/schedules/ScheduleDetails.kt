package com.example.data.models.schedules

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.models.tasks.TimeTaskDetails
import com.example.data.models.tasks.TimeTaskEntity
import com.example.utils.functional.Mapper

data class ScheduleDetails(
    @Embedded
    val dailySchedule : DailyScheduleEntity,
    @Relation(
        parentColumn = "date",
        entityColumn = "daily_schedule_date",
        entity = TimeTaskEntity::class
    )
    val timeTasks : List<TimeTaskDetails>,
    @Relation(
        parentColumn = "date",
        entityColumn = "next_schedule_date",
        entity = TimeTaskEntity::class,
    )
    val overlayTimeTasks: List<TimeTaskDetails>,
){
    fun <T> map(mapper: Mapper<ScheduleDetails, T>) = mapper.map(this)

}
