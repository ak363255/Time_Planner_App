package com.example.impl.data.datasource

interface FeatureCategoryLocalDataSource {
    suspend fun fetchMainCategoryId(): Int?
    fun setMainCategoryId(id:Int?)

    class Base : FeatureCategoryLocalDataSource{
        private var mainCategoryId : Int? = null
        override suspend fun fetchMainCategoryId(): Int? {
            return mainCategoryId
        }

        override fun setMainCategoryId(id: Int?) {
            this.mainCategoryId = id
        }

    }
}