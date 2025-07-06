package com.example.impl.presentation.mappers

import com.example.domain.entities.settings.Settings
import com.example.impl.presentation.models.SettingsUi

fun Settings.mapToUi(): SettingsUi = SettingsUi(
    themeSettings = this.themeSettings.mapToUi(),
    tasksSettings = this.taskSettings.mapToUi()
)