package com.example.impl.presentation.mappers

import com.example.domain.entities.schedules.UndefinedTask
import com.example.impl.presentation.models.tasks.UndefinedTaskUi

internal fun UndefinedTaskUi.mapToDomain() = UndefinedTask(
    id = id,
    createdAt = createdAt,
    deadline = deadline,
    mainCategory = mainCategory.mapToDomain(),
    subCategory = subCategoryUi?.mapToDomain(),
    priority = priority,
    note = note
)

internal fun UndefinedTask.mapToUi() = UndefinedTaskUi(
    id = id,
    createdAt = createdAt,
    deadline = deadline,
    mainCategory = mainCategory.maptoUi(),
    subCategoryUi = subCategory?.mapToUi(),
    priority = priority,
    note = note

)