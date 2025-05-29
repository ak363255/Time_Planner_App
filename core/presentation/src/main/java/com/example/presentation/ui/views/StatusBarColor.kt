package com.example.presentation.ui.views

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.toArgb



@Composable
fun SystemBarColor(
    statusBarColor:Color = MaterialTheme.colorScheme.background ,
    navigationBarColor:Color = MaterialTheme.colorScheme.background,
    isDarkIcon:Boolean
){
    val view = LocalView.current
    val activity = LocalActivity.current as Activity

    SideEffect {
        val window = activity.window
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navigationBarColor.toArgb()

        val insetsController = WindowCompat.getInsetsController(window, view)
        insetsController?.isAppearanceLightStatusBars = isDarkIcon
    }

}