package com.example.impl.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.impl.presentation.theme.tokens.LocalHomeIcons
import com.example.impl.presentation.theme.tokens.LocalHomeStrings
import com.example.impl.presentation.theme.tokens.fetchHomeIcons
import com.example.impl.presentation.theme.tokens.fetchHomeStrings
import com.example.presentation.ui.theme.TimePlannerRes

@Composable
 fun HomeTheme(content: @Composable () -> Unit) {
    val strings = fetchHomeStrings(TimePlannerRes.language)
    val icons = fetchHomeIcons()

    CompositionLocalProvider(
        LocalHomeStrings provides strings,
        LocalHomeIcons provides icons,
        content = content,
    )
}