package com.example.domain.repository

import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory

interface SubCategoriesRepository {
    suspend fun addSubCategories(categories: List<SubCategory>)
    suspend fun fetchSubCategories(type: MainCategory): List<SubCategory>
    suspend fun updateSubCategory(category: SubCategory)
    suspend fun deleteSubCategory(category: SubCategory)
    suspend fun deleteAllSubCategories()
}
