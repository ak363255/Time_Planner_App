package com.example.timeplannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.presentation.ui.views.SystemBarColor
import com.example.timeplannerapp.presentation.ui.theme.TimePlannerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimePlannerAppTheme{
                Box(modifier = Modifier.fillMaxSize()){

                }
                SystemBarColor(
                    statusBarColor = Color(0xFF6200EE),     // Purple
                    navigationBarColor = Color(0xFF121212), // Dark
                    isDarkIcon = true                    // Light icons
                )
            }
        }
    }
}

