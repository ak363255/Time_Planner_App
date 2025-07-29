package com.example.domain.entities.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MainCategory(
    val id : Int =0,
    val customName : String? = null,
    val default  : DefaultCategoryType? = DefaultCategoryType.EMPTY
):Parcelable