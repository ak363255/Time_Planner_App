package com.example.impl.presentation.mappers.categories

import com.example.domain.entities.categories.Categories
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.impl.presentation.models.categories.MainCategoryUi
import com.example.impl.presentation.models.categories.SubCategoryUi
import com.example.impl.presentation.models.categories.CategoriesUi

internal fun MainCategoryUi.mapToDomain() = MainCategory(
    id = id,
    customName = customName,
    default = defaultType
)
internal fun MainCategory.mapToUi() = MainCategoryUi(
    id = id,
    customName = customName,
    defaultType = default,
)

internal fun SubCategoryUi.mapToDomain() = SubCategory(
    id = id,
    name = name,
    mainCategory = mainCategory.mapToDomain(),
    description = description,
)

internal fun SubCategory.mapToUi() = SubCategoryUi(
    id = id,
    name = name,
    mainCategory = mainCategory.mapToUi(),
    description = description,
)

internal fun CategoriesUi.mapToDomain() = Categories(
    category = mainCategory.mapToDomain(),
    subCategories = subCategories.map { it.mapToDomain() },
)