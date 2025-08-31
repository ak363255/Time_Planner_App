package com.example.data.mappers.schedules

import com.example.data.mappers.categories.mapToDomain
import com.example.data.models.tasks.UndefinedTaskDetails
import com.example.data.models.tasks.UndefinedTaskEntity
import com.example.domain.entities.schedules.TaskPriority
import com.example.domain.entities.schedules.UndefinedTask
import com.example.utils.extensions.mapToDate

fun UndefinedTaskDetails.mapToDomain() = UndefinedTask(
    id = task.key,
    createdAt = task.createdAt?.mapToDate(),
    deadline = task.deadline?.mapToDate(),
    mainCategory = mainCategory.mapToDomain(),
    subCategory = subCategory?.mapToDomain(mainCategory.mapToDomain()),
    priority = when{
        task.isImportantMax -> TaskPriority.MAX
        task.isImportantMedium -> TaskPriority.MEDIUM
        else -> TaskPriority.STANDARD
    },
    note = task.note
)
fun UndefinedTask.mapToData() = UndefinedTaskEntity(
    key = id,
    createdAt = createdAt?.time,
    deadline = deadline?.time,
    mainCategoryId = mainCategory.id,
    subCategoryId = subCategory?.id,
    isImportantMax = priority == TaskPriority.MAX,
    isImportantMedium = priority == TaskPriority.MEDIUM,
    note = note
)