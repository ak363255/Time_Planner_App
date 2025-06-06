package com.example.domain.entities.settings

/**
 * Created by Amit on 06-06-2025.
 */
data class ThemeSettings(
    val language : LanguageType = LanguageType.DEFAULT,
    val themeColors : ThemeType = ThemeType.DEFAULT,
    val colorsType : ColorsType = ColorsType.PINK,
    val isDynamicColorEnable : Boolean = false

)