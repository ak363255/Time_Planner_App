package com.example.impl.presentation.models.categories.editor

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 class SubCategoryUi(
    val id:Int = 0,
    val name : String? = null,
    val mainCategory : MainCategoryUi = MainCategoryUi(),
    val description : String? = null
) : Parcelable