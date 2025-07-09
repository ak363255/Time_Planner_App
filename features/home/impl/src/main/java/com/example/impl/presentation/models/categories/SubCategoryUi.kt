package com.example.impl.presentation.models.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubCategoryUi(
    val id: Int = 0,
    val name: String? = null,
    val mainCategory: MainCategoryUi = MainCategoryUi(),
    val description: String? = null
) : Parcelable