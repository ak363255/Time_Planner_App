package com.example.impl.domain.common.editor

import com.example.domain.entities.Template.Template
import com.example.impl.domain.entities.EditModel
import com.example.utils.extensions.changeDay
import com.example.utils.extensions.isCurrentDay
import com.example.utils.extensions.shiftDay
import java.util.Date

internal fun Template.convertToEditModel(date: Date) = EditModel(
    date = date,
    startTime = startTime.changeDay(date),
    endTime = if (endTime.isCurrentDay(startTime)) endTime.changeDay(date) else endTime.changeDay(
        date.shiftDay(1)
    ),
    createdAt = Date(),
    mainCategory = category,
    subCategory = subCategory,
    priority = priority,
    isEnableNotification = isEnableNotification,
    isConsiderInStatistics = isConsiderInStatistics,
    repeatTimes = repeatTimes,
    templateId = templateId,
)


internal fun EditModel.convertToTemplate(id: Int = 0) = Template(
    templateId = id,
    startTime = startTime,
    endTime = endTime,
    category = mainCategory,
    subCategory = subCategory,
    priority = priority,
    isEnableNotification = isEnableNotification,
    isConsiderInStatistics = isConsiderInStatistics,
    repeatTimes = repeatTimes,
)