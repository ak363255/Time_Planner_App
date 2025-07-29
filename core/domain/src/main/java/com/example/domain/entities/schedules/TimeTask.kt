package com.example.domain.entities.schedules

import android.os.Parcelable
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.utils.functional.DateSerializer
import com.example.utils.functional.Mapper
import com.example.utils.functional.TimeRange
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date
@Parcelize
@Serializable
data class TimeTask(
    val key : Long = 0L,
    @Serializable(DateSerializer::class)
    val date : Date,
    @Serializable(DateSerializer::class)
    val createdAt : Date? = null,
    val timeRange : TimeRange,
    val category : MainCategory = MainCategory(),
    val subCategory : SubCategory? = null,
    val isCompleted : Boolean = true,
    val priority : TaskPriority = TaskPriority.STANDARD,
    val isEnableNotification : Boolean = true,
    val taskNotification : TaskNotifications = TaskNotifications(),
    val isConsiderInStatistics : Boolean = true,
    val note : String? = null,
):Parcelable{
    fun <T> ma(mapper : Mapper<TimeTask,T>) = mapper.map(this)
}

