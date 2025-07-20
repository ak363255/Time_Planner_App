package com.example.data.models.tasks

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.models.categories.MainCategoryDetails
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.categories.SubCategoryEntity

data class TimeTaskDetails(
    @Embedded
    val timeTask : TimeTaskEntity,
    @Relation(
        parentColumn = "main_category_id",
        entityColumn = "id",
        entity = MainCategoryEntity::class
    )
    val mainCategory : MainCategoryDetails,
    @Relation(
        parentColumn = "sub_category_id",
        entityColumn = "id",
        entity = SubCategoryEntity::class
    )
    val subCategory: SubCategoryEntity? = null
)
