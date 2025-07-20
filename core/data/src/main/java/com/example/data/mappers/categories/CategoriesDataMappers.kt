package com.example.data.mappers.categories

import com.example.data.models.categories.MainCategoryDetails
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.categories.SubCategoryEntity
import com.example.domain.entities.categories.Categories
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory

fun MainCategoryDetails.mapToDomain() = Categories(
    category = mainCategory.mapToDomain(),
    subCategories = subCategories.map { subCategory ->
        subCategory.mapToDomain(mainCategory.mapToDomain())
    },
)

fun MainCategoryEntity.mapToDomain() = MainCategory(
    id = id,
    customName = customName,
    default = defaultType,
)

fun MainCategory.mapToData() = MainCategoryEntity(
    id = id,
    customName = customName,
    defaultType = default,
)

fun SubCategoryEntity.mapToDomain(mainCategory: MainCategory) = SubCategory(
    id = id,
    mainCategory = mainCategory,
    name = subCategoryName.ifEmpty { null },
    description = description,
)
fun SubCategory.mapToData() = SubCategoryEntity(
    id = id,
    mainCategoryId = mainCategory.id,
    subCategoryName = name ?: "",
    description = description,
)