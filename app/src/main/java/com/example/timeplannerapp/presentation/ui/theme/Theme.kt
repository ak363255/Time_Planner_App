package com.example.timeplannerapp.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.theme.material.ColorsUiType
import com.example.presentation.ui.theme.material.ThemeUiType
import com.example.presentation.ui.theme.material.baseShapes
import com.example.presentation.ui.theme.material.baseTypography
import com.example.presentation.ui.theme.material.toColorScheme
import com.example.presentation.ui.theme.tokens.LanguageUiType
import com.example.presentation.ui.theme.tokens.LocalTimePlannerColorsType
import com.example.presentation.ui.theme.tokens.LocalTimePlannerElevations
import com.example.presentation.ui.theme.tokens.LocalTimePlannerIcons
import com.example.presentation.ui.theme.tokens.LocalTimePlannerLanguage
import com.example.presentation.ui.theme.tokens.LocalTimePlannerStrings
import com.example.presentation.ui.theme.tokens.fetchAppColorsType
import com.example.presentation.ui.theme.tokens.fetchAppElevations
import com.example.presentation.ui.theme.tokens.fetchAppLanguage
import com.example.presentation.ui.theme.tokens.fetchCoreIcons
import com.example.presentation.ui.theme.tokens.fetchCoreStrings

@Composable
fun TimePlannerAppTheme(
    languageType: LanguageUiType = LanguageUiType.DEFAULT,
    themeType : ThemeUiType = ThemeUiType.DEFAULT,
    colors : ColorsUiType = ColorsUiType.PINK,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
){
    val appLanguage = fetchAppLanguage(languageType)
    val coreString = fetchCoreStrings(appLanguage)
    val colorsType = fetchAppColorsType(themeType,colors)
    val appElevations = fetchAppElevations()
    val coreIcons = fetchCoreIcons()
    MaterialTheme(
        colorScheme = themeType.toColorScheme(dynamicColor,colors),
        shapes = baseShapes,
        typography = baseTypography
    ){
        CompositionLocalProvider(
            LocalTimePlannerColorsType provides colorsType,
            LocalTimePlannerLanguage provides appLanguage,
            LocalTimePlannerElevations provides appElevations,
            LocalTimePlannerStrings provides coreString,
            LocalTimePlannerIcons provides coreIcons,
            content = content
        )

    }
}
fun Shapes.full() : RoundedCornerShape = RoundedCornerShape(100.dp)
