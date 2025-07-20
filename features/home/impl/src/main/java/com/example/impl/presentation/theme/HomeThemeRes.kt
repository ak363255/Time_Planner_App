package com.example.impl.presentation.theme

import androidx.compose.runtime.Composable
import com.example.impl.presentation.theme.tokens.HomeIcons
import com.example.impl.presentation.theme.tokens.HomeStrings
import com.example.impl.presentation.theme.tokens.LocalHomeIcons
import com.example.impl.presentation.theme.tokens.LocalHomeStrings

object HomeThemeRes {

    val strings : HomeStrings
        @Composable get() = LocalHomeStrings.current
    val icons : HomeIcons
        @Composable get() = LocalHomeIcons.current
}