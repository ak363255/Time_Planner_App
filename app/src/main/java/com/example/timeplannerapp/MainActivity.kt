package com.example.timeplannerapp

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.views.SystemBarColor
import com.example.timeplannerapp.presentation.ui.main.contract.DeepLinkTarget
import com.example.timeplannerapp.presentation.ui.main.contract.MainDeps
import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.timeplannerapp.presentation.ui.main.contract.MainEvent
import com.example.timeplannerapp.presentation.ui.main.contract.MainViewState
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainEffectCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainStateCommunicator
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainViewmodel
import com.example.timeplannerapp.presentation.ui.splash.SplashContent
import com.example.timeplannerapp.presentation.ui.theme.TimePlannerAppTheme
import com.example.utils.manager.CoroutineManager
import com.example.utils.platform.screen.ScreenContent
import com.example.utils.platform.screen.Test
import com.example.utils.platform.screen.ScreenScope
import com.example.utils.platform.screenmodel.contract.BaseViewState

class MainActivity : ComponentActivity() {
    val mainViewmodelFactory = MainViewmodel.MainViewModelFactory(
        mainStateCommunicator = MainStateCommunicator.Base(),
        mainEffectCommunicator = MainEffectCommunicator.Base(),
        coroutineManager = CoroutineManager.Base()
    )
    val mainViewmodel: MainViewmodel by lazy {
        ViewModelProvider(this,mainViewmodelFactory)[MainViewmodel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenContent(
                screenModel = mainViewmodel,
                initialState = MainViewState(),
                dependencies = MainDeps(screenTarget = DeepLinkTarget.byIntent(intent))
            ) {mainViewState ->
                TimePlannerAppTheme{
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "splash"){
                        composable(route = "splash") {
                              SplashContent()
                        }
                        composable(route = "main") {

                        }
                    }
                }

            }

        }
    }

    @Composable
    fun  ScreenScope<MainViewState, MainEvent, MainEffect, MainDeps>.Splash(mainViewState: MainViewState){
               Box(
                   modifier = Modifier.fillMaxSize()
                       .background(color = Color.Blue)
               ) {

               }
    }


}

