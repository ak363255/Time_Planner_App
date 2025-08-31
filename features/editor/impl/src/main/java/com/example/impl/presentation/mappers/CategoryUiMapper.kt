package com.example.impl.presentation.mappers

import com.example.domain.entities.categories.Categories
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.impl.presentation.models.categories.editor.CategoriesUi
import com.example.impl.presentation.models.categories.editor.MainCategoryUi
import com.example.impl.presentation.models.categories.editor.SubCategoryUi

internal fun MainCategoryUi.mapToDomain() = MainCategory(
    id = id,
    customName = customName,
    default = defaultType
)

internal fun MainCategory.maptoUi() = MainCategoryUi(
    id = id,
    customName = customName,
    defaultType = default
)

internal fun SubCategoryUi.mapToDomain() = SubCategory(
    id = id,
    name = name,
    mainCategory = mainCategory.mapToDomain(),
    description = description
)

internal fun SubCategory.mapToUi() = SubCategoryUi(
    id = id,
    name = name,
    mainCategory = mainCategory.maptoUi(),
    description = description
)

internal fun CategoriesUi.mapToDomain() = Categories(
    category = mainCategory.mapToDomain(),
    subCategories = subCategories.map { it.mapToDomain() }
)

internal fun Categories.mapToUi() = CategoriesUi(
    mainCategory =  category.maptoUi(),
    subCategories = subCategories.map { it.mapToUi() }
)