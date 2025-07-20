package com.example.domain.entities.categories

import kotlinx.serialization.Serializable

@Serializable
data class MainCategory(
    val id : Int =0,
    val customName : String? = null,
    val default  : DefaultCategoryType? = DefaultCategoryType.EMPTY
)