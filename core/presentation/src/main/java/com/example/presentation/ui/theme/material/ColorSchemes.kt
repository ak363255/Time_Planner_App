package com.example.presentation.ui.theme.material

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


enum class ThemeUiType {
    DEFAULT, LIGHT, DARK;

    @Composable
    fun isDarkTheme() = when (this) {
        ThemeUiType.DEFAULT -> isSystemInDarkTheme()
        ThemeUiType.LIGHT -> false
        ThemeUiType.DARK -> true
    }
}


enum class ColorsUiType{
        RED,PINK,PURPLE,BLUE;
        fun seed() = when(this){
            ColorsUiType.RED -> redSeed
            ColorsUiType.PINK ->  pinkSeed
            ColorsUiType.PURPLE -> purpleSeed
            ColorsUiType.BLUE -> blueSeed
        }

        fun onSeed() = when(this){
            ColorsUiType.RED -> red_theme_light_primary
            ColorsUiType.PINK -> pink_theme_light_primary
            ColorsUiType.PURPLE -> purple_theme_light_primary
            ColorsUiType.BLUE -> blue_theme_light_primary
        }

        @Composable
        fun fetchLightColorScheme() = when(this){
            ColorsUiType.RED -> redLightColorScheme
            ColorsUiType.PINK -> pinkLightColorScheme
            ColorsUiType.PURPLE -> purpleLightColorScheme
            ColorsUiType.BLUE -> blueLightColorScheme
        }

        @Composable
        fun fetchDarkColorScheme() = when(this){
            ColorsUiType.RED -> redDarkColorScheme
            ColorsUiType.PINK -> pinkDarkColorScheme
            ColorsUiType.PURPLE -> purpleDarkColorScheme
            ColorsUiType.BLUE -> blueDarkColorScheme
        }

        @Composable
        fun fetchColorScheme(themeType: ThemeUiType) = when(themeType){
            ThemeUiType.DEFAULT -> if(isSystemInDarkTheme()) fetchDarkColorScheme() else fetchLightColorScheme()
            ThemeUiType.LIGHT -> fetchLightColorScheme()
            ThemeUiType.DARK ->  fetchDarkColorScheme()
        }
    }

@Composable
fun ThemeUiType.toColorScheme(
    dynamicColor: Boolean,
    colors: ColorsUiType
): ColorScheme{
    val context = LocalContext.current
    return if(dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        when(this){
            ThemeUiType.DEFAULT -> if(isSystemInDarkTheme()){
                dynamicDarkColorScheme(context)
            }
            else {
                dynamicLightColorScheme(context)
            }
            ThemeUiType.LIGHT -> dynamicLightColorScheme(context)
            ThemeUiType.DARK -> dynamicDarkColorScheme(context)
        }
    }else{
        colors.fetchColorScheme(themeType = this)
    }

}


