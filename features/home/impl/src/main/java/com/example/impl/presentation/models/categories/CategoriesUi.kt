package com.example.impl.presentation.models.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesUi (
    val mainCategory: MainCategoryUi,
    val subCategories : List<SubCategoryUi>
): Parcelable