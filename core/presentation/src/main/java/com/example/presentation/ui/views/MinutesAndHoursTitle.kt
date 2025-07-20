package com.example.presentation.ui.views

import androidx.compose.runtime.Composable
import com.example.presentation.ui.theme.TimePlannerRes
import com.example.utils.extensions.toMinutesOrHoursString

@Composable
fun Long.toMinutesOrHoursTitle(): String{
    val minutesSymbols = TimePlannerRes.strings.minutesSymbol
    val hoursSymbols = TimePlannerRes.strings.hoursSymbol
    return this.toMinutesOrHoursString(minutesSymbols,hoursSymbols)
}