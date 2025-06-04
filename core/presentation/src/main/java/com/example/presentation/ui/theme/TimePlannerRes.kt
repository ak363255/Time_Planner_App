package com.example.presentation.ui.theme

import androidx.compose.runtime.Composable
import com.example.presentation.ui.theme.tokens.LocalTimePlannerColorsType
import com.example.presentation.ui.theme.tokens.LocalTimePlannerElevations
import com.example.presentation.ui.theme.tokens.LocalTimePlannerIcons
import com.example.presentation.ui.theme.tokens.LocalTimePlannerLanguage
import com.example.presentation.ui.theme.tokens.LocalTimePlannerStrings
import com.example.presentation.ui.theme.tokens.TimePlannerColorsType
import com.example.presentation.ui.theme.tokens.TimePlannerElevations
import com.example.presentation.ui.theme.tokens.TimePlannerIcons
import com.example.presentation.ui.theme.tokens.TimePlannerLanguage
import com.example.presentation.ui.theme.tokens.TimePlannerStrings

object TimePlannerRes {
    val elevation : TimePlannerElevations
        @Composable get() = LocalTimePlannerElevations.current

    val colorsType : TimePlannerColorsType
        @Composable get() = LocalTimePlannerColorsType.current

    val language : TimePlannerLanguage
        @Composable get() = LocalTimePlannerLanguage.current

    val strings : TimePlannerStrings
        @Composable get() = LocalTimePlannerStrings.current

    val icons : TimePlannerIcons
        @Composable get() = LocalTimePlannerIcons.current
}