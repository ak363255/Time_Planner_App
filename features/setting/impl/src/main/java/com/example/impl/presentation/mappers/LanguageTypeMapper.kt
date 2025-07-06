package com.example.impl.presentation.mappers

import com.example.domain.entities.settings.LanguageType
import com.example.presentation.ui.theme.tokens.LanguageUiType

fun LanguageType.mapToUi(): LanguageUiType = LanguageUiType.entries.find { this.code == it.code }!!
