package com.example.timeplannerapp.presentation.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.ui.theme.TimePlannerRes
import com.example.presentation.ui.theme.material.onSplashGradient
import com.example.presentation.ui.theme.material.splashGradientColors
import com.example.utils.functional.Constants
import kotlinx.coroutines.delay


@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    onEnd : ()-> Unit
) {

    var isVisibleLogo by remember { mutableStateOf(false) }
    var isVisibleText by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(splashGradientColors)
        ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(visible = isVisibleLogo,enter = fadeIn()) {
               Icon(
                   modifier = Modifier.size(86.dp),
                   contentDescription = TimePlannerRes.strings.appName,
                   painter = painterResource(id = TimePlannerRes.icons.splashIcon),
                   tint = onSplashGradient

               )
            }
            AnimatedVisibility(visible = isVisibleText,enter = expandHorizontally()) {
                Text(
                    text = Constants.App.SPLASH_NAME,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        lineHeight = 35.sp
                    ),
                    color = onSplashGradient
                )
            }

        }
    }
    LaunchedEffect(Unit) {
        delay(Constants.Delay.SPLASH_LOGO)
        isVisibleLogo = true
        delay(Constants.Delay.SPLASH_TEXT)
        isVisibleText = true
        delay(Constants.Delay.SPLASH_NAV)
        onEnd()
    }

}