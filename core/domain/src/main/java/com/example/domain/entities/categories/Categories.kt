package com.example.domain.entities.categories

data class Categories(
    val category : MainCategory = MainCategory(),
    val subCategories : List<SubCategory> = emptyList()
)

data class CategoriesTest(
    val mainCategory: MainCategoryTest = MainCategoryTest(),
    val subCategories: List<SubCategory> = emptyList(),
)

data class MainCategoryTest(
    val id: Int = 0,
    val customName: String? = null,
    val defaultType: DefaultCategoryType? = DefaultCategoryType.EMPTY,
)
