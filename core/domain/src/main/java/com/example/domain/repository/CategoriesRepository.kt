package com.example.domain.repository

import com.example.domain.entities.categories.Categories
import com.example.domain.entities.categories.MainCategory
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun addMainCategories(categories : List<MainCategory>):List<Int>
    suspend fun fetchCategories(): Flow<List<Categories>>
    suspend fun fetchCategoriesByType(mainCategory: MainCategory): Categories?
    suspend fun updateMainCategory(category : MainCategory)
    suspend fun deleteMainCategory(category: MainCategory)
    suspend fun deleteAllCategories()
}