package com.example.timeplannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.timeplannerapp.presentation.ui.main.contract.DeepLinkTarget
import com.example.timeplannerapp.presentation.ui.main.contract.MainDeps
import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.timeplannerapp.presentation.ui.main.contract.MainEvent
import com.example.timeplannerapp.presentation.ui.main.contract.MainRoute
import com.example.timeplannerapp.presentation.ui.main.contract.MainViewState
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainViewmodel
import com.example.timeplannerapp.presentation.ui.splash.SplashContent
import com.example.timeplannerapp.presentation.ui.tabs.TabScreen
import com.example.timeplannerapp.presentation.ui.theme.TimePlannerAppTheme
import com.example.utils.platform.screen.ScreenContent
import com.example.utils.platform.screen.ScreenScope
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    val mainViewmodel: MainViewmodel by viewModel()
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
                    NavHost(navController = navController, startDestination = MainRoute.Splash){
                        composable<MainRoute.Splash> {
                              SplashContent{
                                   dispatchEvent(MainEvent.NavigateToMain)
                              }
                        }
                        composable<MainRoute.Home> {
                            TabScreen(tabViewModel = koinViewModel())
                        }
                    }
                    handleEffect { effect ->
                        when(effect){
                            MainEffect.NavigateToEditor -> {
                            }
                            MainEffect.NavigateToMain -> {
                                navController.navigate(MainRoute.Home, navOptions {
                                    popUpTo(0){inclusive = true}
                                    launchSingleTop = true
                                })

                            }
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

