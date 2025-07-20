package com.example.data.models.categories

import androidx.room.Embedded
import androidx.room.Relation

data class MainCategoryDetails(
    @Embedded
    val mainCategory : MainCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "main_category_id",
        entity = SubCategoryEntity::class,
    )
    val subCategories : List<SubCategoryEntity>
)
