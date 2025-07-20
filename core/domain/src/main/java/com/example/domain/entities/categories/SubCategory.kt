package com.example.domain.entities.categories

data class SubCategory (
    val id:Int = 0,
    val mainCategory : MainCategory = MainCategory(),
    val name : String? = null,
    val description : String? = null
)