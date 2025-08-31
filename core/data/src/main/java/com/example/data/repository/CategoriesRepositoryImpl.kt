package com.example.data.repository

import com.example.data.datasources.categories.CategoriesLocalDataSource
import com.example.data.mappers.categories.mapToData
import com.example.data.mappers.categories.mapToDomain
import com.example.domain.entities.categories.Categories
import com.example.domain.entities.categories.MainCategory
import com.example.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.text.toInt

class CategoriesRepositoryImpl(
    private val localDataSource : CategoriesLocalDataSource
): CategoriesRepository {
    override suspend fun addMainCategories(categories: List<MainCategory>): List<Int> {
        return localDataSource.addMainCategories(categories.map { it.mapToData() }).map { it.toInt() }
    }

    override suspend fun fetchCategories(): Flow<List<Categories>> {
        return localDataSource.fetchMainCategories().map { categories ->
            categories.map { it.mapToDomain() }
        }
    }

    override suspend fun fetchCategoriesByType(mainCategory: MainCategory): Categories? {
        return localDataSource.fetchCategoriesByType(mainCategory)?.mapToDomain()
    }

    override suspend fun updateMainCategory(category: MainCategory) {
        localDataSource.updateMainCategory(category.mapToData())
    }

    override suspend fun deleteMainCategory(category: MainCategory) {
        localDataSource.removeMainCategory(category.id)
    }

    override suspend fun deleteAllCategories() {
        localDataSource.removeAllCategories()
    }
}