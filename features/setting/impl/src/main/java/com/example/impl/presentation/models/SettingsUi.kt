package com.example.impl.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
 data class SettingsUi(
    val themeSettings : ThemeSettingsUi,
    val tasksSettings : TaskSettingsUi,
): Parcelable