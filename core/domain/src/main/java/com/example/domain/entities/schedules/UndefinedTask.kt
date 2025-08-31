package com.example.domain.entities.schedules

import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.utils.functional.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class UndefinedTask(
    val id : Long = 0L,
    @Serializable(DateSerializer::class) val createdAt : Date? = null,
    @Serializable(DateSerializer::class) val deadline : Date? = null,
    val mainCategory: MainCategory,
    val subCategory : SubCategory? = null,
    val priority : TaskPriority = TaskPriority.STANDARD,
    val note : String? = null
)