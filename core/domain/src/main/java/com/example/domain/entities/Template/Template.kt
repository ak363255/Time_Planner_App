package com.example.domain.entities.Template

import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.domain.entities.schedules.TaskPriority
import com.example.domain.entities.schedules.TimeTask
import com.example.utils.extensions.compareByHoursAndMinutes
import com.example.utils.functional.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import com.example.utils.functional.Mapper

data class Template(
    val templateId: Int,
    @Serializable(DateSerializer::class)
    val startTime: Date,
    @Serializable
    val endTime: Date,
    val category: MainCategory,
    val subCategory: SubCategory?,
    val priority: TaskPriority = TaskPriority.STANDARD,
    val isEnableNotification: Boolean = true,
    val isConsiderInStatistics: Boolean = true,
    val repeatEnabled: Boolean = false,
    val repeatTimes: List<RepeatTime>
) {
    fun <T> map(mapper: Mapper<Template, T>) = mapper.map(this)
    fun checkDateIsRepeat(date: Date): Boolean {
        return repeatEnabled && repeatTimes.find { it.checkDateIsRepeat(date) } != null
    }

    fun equalsTemplate(timeTask: TimeTask)  =
        startTime.compareByHoursAndMinutes(timeTask.timeRange.from) &&
        endTime.compareByHoursAndMinutes(timeTask.timeRange.to) &&
        category.id == timeTask.category.id &&
        subCategory == timeTask.subCategory &&
        priority == timeTask.priority &&
        isEnableNotification == timeTask.isEnableNotification &&
        isConsiderInStatistics == timeTask.isConsiderInStatistics
}