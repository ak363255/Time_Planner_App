package com.example.impl.presentation.mappers.categories

import com.example.domain.entities.categories.Categories
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.impl.presentation.models.categories.CategoriesUi
import com.example.impl.presentation.models.categories.MainCategoryUi
import com.example.impl.presentation.models.categories.SubCategoryUi

fun MainCategoryUi.mapToDomain() = MainCategory(
    id = id,
    customName = customName,
    default = defaultType
)
fun MainCategory.mapToUi() = MainCategoryUi(
    id = id,
    customName = customName,
    defaultType = default,
)

fun SubCategoryUi.mapToDomain() = SubCategory(
    id = id,
    name = name,
    mainCategory = mainCategory.mapToDomain(),
    description = description,
)

fun SubCategory.mapToUi() = SubCategoryUi(
    id = id,
    name = name,
    mainCategory = mainCategory.mapToUi(),
    description = description,
)

fun CategoriesUi.mapToDomain() = Categories(
    category = mainCategory.mapToDomain(),
    subCategories = subCategories.map { it.mapToDomain() },
)