package com.example.impl.presentation.models.categories

import android.os.Parcelable
import androidx.compose.runtime.Composable
import com.example.domain.entities.categories.DefaultCategoryType
import com.example.presentation.ui.mappers.mapToName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainCategoryUi(
    val id : Int = 0,
    val customName : String? = null,
    val defaultType : DefaultCategoryType? = DefaultCategoryType.EMPTY
): Parcelable{
    @Composable
    fun fetchName() = when(customName != null && customName != "null"){
        true ->customName
        false -> defaultType?.mapToName()
    }
}