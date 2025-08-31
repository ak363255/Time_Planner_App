package com.example.data.datasources.subcategories

import com.example.data.models.categories.SubCategoryEntity
import com.example.domain.entities.categories.MainCategory

interface SubCategoriesLocalDataSource {

    suspend fun addSubCategories(subCategories: List<SubCategoryEntity>)
    suspend fun fetchSubCategoriesByType(type: MainCategory): List<SubCategoryEntity>
    suspend fun updateSubCategory(subCategory: SubCategoryEntity)
    suspend fun removeSubCategory(id: Int)
    suspend fun removeAllSubCategories()

    class Base(
        private val subCategoriesDao: SubCategoriesDao
    ) : SubCategoriesLocalDataSource{
        override suspend fun addSubCategories(subCategories: List<SubCategoryEntity>) {
            subCategoriesDao.addSubCategories(subCategories)
        }

        override suspend fun fetchSubCategoriesByType(type: MainCategory): List<SubCategoryEntity> {
            return subCategoriesDao.fetchSubCategoriesByTypeId(type.id)
        }

        override suspend fun updateSubCategory(subCategory: SubCategoryEntity) {
            subCategoriesDao.updateSubCategory(subCategory)
        }

        override suspend fun removeSubCategory(id: Int) {
            subCategoriesDao.removeSubCategory(id)
        }

        override suspend fun removeAllSubCategories() {
            subCategoriesDao.removeAllSubCategories()
        }

    }
}