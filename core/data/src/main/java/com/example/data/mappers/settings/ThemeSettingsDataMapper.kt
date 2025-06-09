package com.example.data.mappers.settings

import com.example.data.models.settings.ThemeSettingEntity
import com.example.domain.entities.settings.ThemeSettings


fun ThemeSettingEntity.mapToDomain(): ThemeSettings = ThemeSettings(
        language = this.language,
        themeColors = this.themeColors,
        colorsType = this.colorsType,
        isDynamicColorEnable = this.isDynamicColorEnable
    )

fun ThemeSettings.mapToData() = ThemeSettingEntity(
    id = 0,
    language = language,
    themeColors = themeColors,
    colorsType = colorsType,
    isDynamicColorEnable = isDynamicColorEnable
)
