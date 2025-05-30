package com.example.presentation.ui.theme.tokens

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TimePlannerElevations(
    val levelZero: Dp,
    val levelOne: Dp,
    val levelTwo: Dp,
    val levelThree: Dp,
    val levelFour: Dp,
    val levelFive: Dp,
)

val baseTimePlannerElevations = TimePlannerElevations(
    levelZero = 0.dp,
    levelOne = 1.dp,
    levelTwo = 3.dp,
    levelThree = 6.dp,
    levelFour = 8.dp,
    levelFive = 12.dp,
)

val LocalTimePlannerElevations = staticCompositionLocalOf<TimePlannerElevations> {
    error("Elevations is not provided")
}

fun fetchAppElevations() = baseTimePlannerElevations
