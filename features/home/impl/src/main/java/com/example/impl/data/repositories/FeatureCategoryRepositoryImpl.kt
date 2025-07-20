package com.example.impl.data.repositories

import com.example.impl.data.datasource.FeatureCategoryLocalDataSource
import com.example.impl.domain.repository.FeatureCategoryRepository
import com.example.impl.domain.repository.FeatureScheduleRepository
import java.util.Date

class FeatureCategoryRepositoryImpl(
    private val localDataSource : FeatureCategoryLocalDataSource
) : FeatureCategoryRepository{
    override suspend fun fetchMainCategoryId(): Int? {
        return localDataSource.fetchMainCategoryId()
    }

    override fun setMainCategoryId(id: Int?) {
        localDataSource.setMainCategoryId(id)
    }

}