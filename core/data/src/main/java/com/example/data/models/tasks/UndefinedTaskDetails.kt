package com.example.data.models.tasks

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.categories.SubCategoryEntity
import com.example.domain.entities.categories.SubCategory

data class UndefinedTaskDetails(
    @Embedded
    val task : UndefinedTaskEntity,

    @Relation(
        parentColumn = "main_category_id",
        entityColumn = "id",
        entity = MainCategoryEntity::class,

    )
    val mainCategory : MainCategoryEntity,
    @Relation(
        parentColumn = "sub_category_id",
        entityColumn = "id",
        entity = SubCategoryEntity::class
    )
    val subCategory : SubCategoryEntity?

)
