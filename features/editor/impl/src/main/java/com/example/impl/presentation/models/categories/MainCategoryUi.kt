package com.example.impl.presentation.models.categories

import android.os.Parcelable
import androidx.compose.runtime.Composable
import com.example.domain.entities.categories.DefaultCategoryType
import com.example.presentation.ui.mappers.mapToName
import com.example.presentation.ui.mappers.mapToString
import com.example.presentation.ui.theme.tokens.TimePlannerStrings
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainCategoryUi(
    val id : Int = 0,
    val customName : String? = null,
    val defaultType : DefaultCategoryType? = DefaultCategoryType.EMPTY
): Parcelable{
    fun fetchName(strings : TimePlannerStrings) = when(customName !=null && customName != "null"){
        true ->customName
        false -> defaultType?.mapToString(strings)
    }

    @Composable
    fun fetchName() = when(customName != null && customName != "null"){
        true -> customName
        false -> defaultType?.mapToName()
    }
}