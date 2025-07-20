package com.example.impl.domain.repository

interface FeatureCategoryRepository {
    suspend fun fetchMainCategoryId(): Int?
    fun setMainCategoryId(id: Int?)
}