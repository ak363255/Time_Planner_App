package com.example.impl.domain.common

import com.example.domain.entities.Template.Template
import com.example.domain.entities.schedules.TimeTask
import com.example.utils.extensions.changeDay
import com.example.utils.extensions.generateUniqueKey
import com.example.utils.extensions.isCurrentDay
import com.example.utils.extensions.shiftDay
import com.example.utils.functional.TimeRange
import java.util.Date

 fun Template.convertToTimeTask(
    date: Date,
    key: Long = generateUniqueKey(),
    createdAt: Date? = Date(),
) = TimeTask(
    key = key,
    date = date,
    timeRange = TimeRange(
        from = startTime.changeDay(date),
        to = if (endTime.isCurrentDay(startTime)) endTime.changeDay(date) else endTime.changeDay(date.shiftDay(1)),
    ),
    createdAt = createdAt,
    category = category,
    subCategory = subCategory,
    priority = priority,
    isEnableNotification = isEnableNotification,
    isConsiderInStatistics = isConsiderInStatistics,
)