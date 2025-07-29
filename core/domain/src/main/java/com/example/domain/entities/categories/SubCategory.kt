package com.example.domain.entities.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SubCategory (
    val id:Int = 0,
    val mainCategory : MainCategory = MainCategory(),
    val name : String? = null,
    val description : String? = null
):Parcelable