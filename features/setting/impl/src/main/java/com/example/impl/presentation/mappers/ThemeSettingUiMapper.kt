package com.example.impl.presentation.mappers

import com.example.domain.entities.settings.ColorsType
import com.example.domain.entities.settings.ThemeSettings
import com.example.domain.entities.settings.ThemeType
import com.example.impl.presentation.models.ThemeSettingsUi
import com.example.presentation.ui.theme.material.ColorsUiType
import com.example.presentation.ui.theme.material.ThemeUiType


fun ThemeSettings.mapToUi(): ThemeSettingsUi = ThemeSettingsUi(
    language = this.language.mapToUi(),
    themeColors = this.themeColors.mapToUi(),
    colorsType = this.colorsType.mapToUi(),
    isDynamicColor = this.isDynamicColorEnable,
)

fun ThemeType.mapToUi(): ThemeUiType = when (this) {
    ThemeType.DEFAULT -> ThemeUiType.DEFAULT
    ThemeType.LIGHT -> ThemeUiType.LIGHT
    ThemeType.DARK -> ThemeUiType.DARK
}

fun ThemeUiType.toDomain(): ThemeType = when(this){
    ThemeUiType.DEFAULT -> ThemeType.DEFAULT
    ThemeUiType.LIGHT -> ThemeType.LIGHT
    ThemeUiType.DARK -> ThemeType.DARK
}

fun ColorsType.mapToUi(): ColorsUiType = when (this) {
    ColorsType.PINK -> ColorsUiType.PINK
    ColorsType.PURPLE -> ColorsUiType.PURPLE
    ColorsType.RED -> ColorsUiType.RED
    ColorsType.BLUE -> ColorsUiType.BLUE
}

fun ColorsUiType.toDomain() : ColorsType = when(this){
    ColorsUiType.RED -> ColorsType.RED
    ColorsUiType.PINK -> ColorsType.PINK
    ColorsUiType.PURPLE -> ColorsType.PURPLE
    ColorsUiType.BLUE -> ColorsType.BLUE
}