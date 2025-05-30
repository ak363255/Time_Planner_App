package com.example.presentation.ui.theme.tokens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.presentation.ui.theme.material.ColorsUiType
import com.example.presentation.ui.theme.material.ThemeUiType


data class TimePlannerColorsType(
    val isDark: Boolean,
    val colors : ColorsUiType
)

val LocalTimePlannerColorsType = staticCompositionLocalOf<TimePlannerColorsType>{
    error("Colors type is not provided")
}

@Composable
fun fetchAppColorsType(
    themeType: ThemeUiType,
    colors: ColorsUiType
) = TimePlannerColorsType(
    isDark =  themeType.isDarkTheme(),
    colors = colors
)
