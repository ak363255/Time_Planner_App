package com.example.impl.presentation.models.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class CategoriesUi (
    val mainCategory: MainCategoryUi,
    val subCategories : List<SubCategoryUi>
): Parcelable
//2 home