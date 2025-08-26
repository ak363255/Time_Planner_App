package com.example.timeplannerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.domain.entities.schedules.TimeTask
import com.example.domain.repository.TasksSettingsRepository
import com.example.domain.repository.ThemeSettingsRepository
import com.example.impl.domain.common.SettingsEitherWrapper
import com.example.presentation.ui.contract.MainRoute
import com.example.presentation.ui.theme.tokens.MainNavController
import com.example.timeplannerapp.domain.interactors.SettingsInteractor
import com.example.timeplannerapp.presentation.ui.main.contract.DeepLinkTarget
import com.example.timeplannerapp.presentation.ui.main.contract.MainDeps
import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.timeplannerapp.presentation.ui.main.contract.MainEvent
import com.example.timeplannerapp.presentation.ui.main.contract.MainViewState
import com.example.timeplannerapp.presentation.ui.main.viewmodel.MainViewmodel
import com.example.timeplannerapp.presentation.ui.main.viewmodel.SettingsWorkProcessor
import com.example.timeplannerapp.presentation.ui.splash.SplashContent
import com.example.timeplannerapp.presentation.ui.tabs.TabScreen
import com.example.timeplannerapp.presentation.ui.theme.TimePlannerAppTheme
import com.example.utils.functional.deserialize
import com.example.utils.platform.screen.ScreenContent
import com.example.utils.platform.screen.ScreenScope
import org.koin.android.ext.android.getKoin
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
  companion object{
      init {
          System.loadLibrary("native-lib") //load the library
      }
  }
    external fun getApiKey(id:Int) : String

    val mainViewmodel: MainViewmodel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = getApiKey(1);
        Log.d("API_KEY","apikey is -> ${apiKey}");
        enableEdgeToEdge()
        setContent {
            ScreenContent(
                screenModel = mainViewmodel,
                initialState = MainViewState(),
                dependencies = MainDeps(screenTarget = DeepLinkTarget.byIntent(intent))
            ) {mainViewState ->
                TimePlannerAppTheme{
                    val navController = rememberNavController()
                    CompositionLocalProvider(MainNavController provides navController) {
                        NavHost(navController = navController, startDestination = MainRoute.Splash){
                            composable<MainRoute.Splash> {
                                SplashContent{
                                    dispatchEvent(MainEvent.NavigateToMain)
                                }
                            }
                            composable<MainRoute.Home> {
                                TabScreen(tabViewModel = koinViewModel())
                            }
                            composable<MainRoute.NavigateToEditorCreator> {backStackEntry ->

                                val timeTask = backStackEntry.arguments?.getString("timeTask")
                                val timeTaskModel : TimeTask? = if(timeTask != null) deserialize(timeTask) else null
                                Box(modifier = Modifier.fillMaxSize().background(color = Color.Red)){
                                 //   Text(text = " ${user?.timeRange?.from} null",color = Color.Black, fontSize = 12.sp)

                                }
                            }
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
}

