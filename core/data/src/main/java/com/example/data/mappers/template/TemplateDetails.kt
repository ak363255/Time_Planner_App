package com.example.data.mappers.template

import com.example.data.mappers.categories.mapToDomain
import com.example.data.models.template.TemplateCompound
import com.example.data.models.template.TemplateDetails
import com.example.data.models.template.TemplateEntity
import com.example.domain.entities.Template.Template
import com.example.domain.entities.schedules.TaskPriority
import com.example.utils.extensions.mapToDate


fun TemplateDetails.mapToDomain() = Template(
    startTime = template.startTime.mapToDate(),
    endTime = template.endTime.mapToDate(),
    category = mainCategory.mapToDomain(),
    subCategory = subCategory?.mapToDomain(mainCategory.mapToDomain()),
    priority = when {
        template.isImportantMax -> TaskPriority.MAX
        template.isImportantMedium -> TaskPriority.MEDIUM
        else -> TaskPriority.STANDARD
    },
    isEnableNotification = template.isEnableNotification,
    isConsiderInStatistics = template.isConsiderInStatistics,
    templateId = template.id,
    repeatEnabled = template.repeatEnabled,
    repeatTimes = repeatTime.let { list ->
        list.map { repeatTimeEntity -> repeatTimeEntity.mapToDomain() }
    },
)

fun Template.mapToData() = TemplateCompound(
    template = TemplateEntity(
        id = templateId,
        startTime = startTime.time,
        endTime = endTime.time,
        categoryId = category.id,
        subCategoryId = subCategory?.id,
        isImportantMedium = priority == TaskPriority.MEDIUM,
        isImportantMax = priority == TaskPriority.MAX,
        isEnableNotification = isEnableNotification,
        isConsiderInStatistics = isConsiderInStatistics,
        repeatEnabled = repeatEnabled,
    ),
    repeatTimes = repeatTimes.map {
        it.mapToData(templateId)
    },
)