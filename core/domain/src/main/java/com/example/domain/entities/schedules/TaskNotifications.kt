package com.example.domain.entities.schedules

import android.os.Parcelable
import com.example.utils.extensions.shiftDay
import com.example.utils.extensions.shiftHours
import com.example.utils.extensions.shiftMillis
import com.example.utils.extensions.shiftMinutes
import com.example.utils.functional.TimeRange
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class TaskNotifications(
    val fifteenMinutesBefore : Boolean = false,
    val oneHourBefore: Boolean = false,
    val threeHourBefore: Boolean = false,
    val oneDayBefore: Boolean = false,
    val oneWeekBefore: Boolean = false,
    val beforeEnd: Boolean = false,
):Parcelable{
    fun toTypes(enabledNotifications: Boolean) = mutableListOf<TaskNotificationType>().apply {
        if (enabledNotifications) {
            add(TaskNotificationType.START)
            if (fifteenMinutesBefore) add(TaskNotificationType.FIFTEEN_MINUTES_BEFORE)
            if (oneHourBefore) add(TaskNotificationType.ONE_HOUR_BEFORE)
            if (threeHourBefore) add(TaskNotificationType.THREE_HOUR_BEFORE)
            if (oneDayBefore) add(TaskNotificationType.ONE_DAY_BEFORE)
            if (oneWeekBefore) add(TaskNotificationType.ONE_WEEK_BEFORE)
            if (beforeEnd) add(TaskNotificationType.AFTER_START_BEFORE_END)
        }
    }.toList()
}

enum class TaskNotificationType(val idAmount: Long) {
    START(0),
    FIFTEEN_MINUTES_BEFORE(60L),
    ONE_HOUR_BEFORE(10L),
    THREE_HOUR_BEFORE(20L),
    ONE_DAY_BEFORE(30L),
    ONE_WEEK_BEFORE(50L),
    AFTER_START_BEFORE_END(40L);

    fun fetchNotifyTrigger(timeRange: TimeRange) = when (this) {
        START -> timeRange.from
        FIFTEEN_MINUTES_BEFORE -> timeRange.from.shiftMinutes(-15)
        ONE_HOUR_BEFORE -> timeRange.from.shiftHours(-1)
        THREE_HOUR_BEFORE -> timeRange.from.shiftHours(-3)
        ONE_DAY_BEFORE -> timeRange.from.shiftDay(-1)
        ONE_WEEK_BEFORE -> timeRange.from.shiftDay(-7)
        AFTER_START_BEFORE_END -> timeRange.to.shiftMillis(-10000)
    }
}
