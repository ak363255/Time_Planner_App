package com.example.data.models.template

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.categories.SubCategoryEntity
import com.example.domain.entities.Template.RepeatTime

data class TemplateDetails(
    @Embedded
    val template : TemplateEntity,
    @Relation(
        parentColumn ="main_category_id",
        entity = MainCategoryEntity::class,
        entityColumn = "id"
    )
    val mainCategory: MainCategoryEntity,
    @Relation(
        parentColumn = "sub_category_id",
        entityColumn = "id",
        entity = SubCategoryEntity::class,
    )
    val subCategory : SubCategoryEntity?,
    @Relation(
        parentColumn ="id",
        entityColumn = "template_id",
        entity = RepeatTimeEntity::class
    )
    val repeatTime: List<RepeatTimeEntity>
)