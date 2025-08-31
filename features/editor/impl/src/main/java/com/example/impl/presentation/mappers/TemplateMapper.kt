package com.example.impl.presentation.mappers

import com.example.domain.entities.Template.Template
import com.example.impl.presentation.models.template.TemplateUi
import kotlin.text.category


internal fun TemplateUi.mapToDomain() = Template(
    templateId = templateId,
    startTime = startTime,
    endTime = endTime,
    category = category.mapToDomain(),
    subCategory = subCategory?.mapToDomain(),
    priority = priority,
    isEnableNotification = isEnableNotification,
    isConsiderInStatistics = isConsiderInStatistics,
    repeatEnabled = repeatEnabled,
    repeatTimes = repeatTimes,
)


internal fun Template.mapToUi() = TemplateUi(
    templateId = templateId,
    startTime = startTime,
    endTime = endTime,
    category = category.maptoUi(),
    subCategory = subCategory?.mapToUi(),
    priority = priority,
    isEnableNotification = isEnableNotification,
    isConsiderInStatistics = isConsiderInStatistics,
    repeatEnabled = repeatEnabled,
    repeatTimes = repeatTimes,
)