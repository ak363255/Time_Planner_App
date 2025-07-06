package com.example.impl.presentation.models

import android.os.Parcelable
import com.example.presentation.ui.theme.material.ColorsUiType
import com.example.presentation.ui.theme.material.ThemeUiType
import com.example.presentation.ui.theme.tokens.LanguageUiType
import com.google.android.material.color.utilities.DynamicColor
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThemeSettingsUi(
    val language : LanguageUiType = LanguageUiType.DEFAULT,
    val themeColors : ThemeUiType = ThemeUiType.DEFAULT,
    val colorsType : ColorsUiType = ColorsUiType.PINK,
    val isDynamicColor: Boolean = false,
): Parcelable