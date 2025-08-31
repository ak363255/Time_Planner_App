package com.example.data.repository

import com.example.data.datasources.subcategories.SubCategoriesLocalDataSource
import com.example.data.mappers.categories.mapToData
import com.example.data.mappers.categories.mapToDomain
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.domain.repository.SubCategoriesRepository

class SubCategoriesRepositoryImpl(
    private val localDataSource : SubCategoriesLocalDataSource
): SubCategoriesRepository {
    override suspend fun addSubCategories(categories: List<SubCategory>) {
        localDataSource.addSubCategories(categories.map { it.mapToData() })
    }

    override suspend fun fetchSubCategories(type: MainCategory): List<SubCategory> {
        return localDataSource.fetchSubCategoriesByType(type).map { subCategory ->
            subCategory.mapToDomain(type)
        }    }

    override suspend fun updateSubCategory(category: SubCategory) {
        localDataSource.updateSubCategory(category.mapToData())
    }

    override suspend fun deleteSubCategory(category: SubCategory) {
        localDataSource.removeSubCategory(category.id)
    }

    override suspend fun deleteAllSubCategories() {
        localDataSource.removeAllSubCategories()
    }
}