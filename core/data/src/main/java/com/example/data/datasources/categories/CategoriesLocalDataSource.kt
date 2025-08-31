package com.example.data.datasources.categories

import com.example.data.models.categories.MainCategoryDetails
import com.example.data.models.categories.MainCategoryEntity
import com.example.domain.entities.categories.MainCategory
import kotlinx.coroutines.flow.Flow

interface CategoriesLocalDataSource {
    suspend fun addMainCategory(mainCategory: MainCategoryEntity): Long
    suspend fun addMainCategories(mainCategories : List<MainCategoryEntity>):List<Long>
    fun fetchMainCategories(): Flow<List<MainCategoryDetails>>
    suspend fun fetchCategoriesByType(mainCategory: MainCategory): MainCategoryDetails?
    suspend fun updateMainCategory(mainCategory: MainCategoryEntity)
    suspend fun removeMainCategory(id: Int)
    suspend fun removeAllCategories()

    class Base(
        private val mainCategoriesDao: MainCategoriesDao
    ): CategoriesLocalDataSource{
        override suspend fun addMainCategory(mainCategory: MainCategoryEntity): Long {
            return mainCategoriesDao.addCategory(mainCategory)
        }

        override suspend fun addMainCategories(mainCategories: List<MainCategoryEntity>): List<Long> {
            return mainCategoriesDao.addCategories(mainCategories)
        }

        override fun fetchMainCategories(): Flow<List<MainCategoryDetails>> {
            return mainCategoriesDao.fetchAllCategories()
        }

        override suspend fun fetchCategoriesByType(mainCategory: MainCategory): MainCategoryDetails? {
            return mainCategoriesDao.fetchCategoriesById(mainCategory.id)
        }

        override suspend fun updateMainCategory(mainCategory: MainCategoryEntity) {
            mainCategoriesDao.updateCategory(mainCategory)
        }

        override suspend fun removeMainCategory(id: Int) {
            mainCategoriesDao.removeCategory(id)
        }

        override suspend fun removeAllCategories() {
            mainCategoriesDao.removeAllCategories()
        }

    }

}