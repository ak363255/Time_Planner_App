package com.example.impl.presentation.ui.home.views

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.impl.presentation.theme.HomeThemeRes
import com.example.impl.presentation.ui.home.contract.HomeEffect
import com.example.impl.presentation.ui.home.contract.HomeEvent
import com.example.impl.presentation.ui.home.contract.HomePageRoute
import com.example.impl.presentation.ui.home.contract.HomeViewState
import com.example.impl.presentation.ui.home.screenModel.HomeScreenModel
import com.example.presentation.ui.contract.MainRoute
import com.example.presentation.ui.theme.tokens.MainNavController
import com.example.presentation.ui.views.ErrorSnackbar
import com.example.utils.extensions.startThisDay
import com.example.utils.managers.LocalDrawerManager
import com.example.utils.platform.screen.ScreenContent
import com.example.utils.platform.screenmodel.EmptyDeps
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun HomeScreen(
    screenModel: HomeScreenModel,
    initialState: HomeViewState = HomeViewState()
) {

    ScreenContent(
        screenModel = screenModel,
        initialState = initialState,
        dependencies = EmptyDeps
    ) { state ->
        val mainNavController = MainNavController.current
        val scope = rememberCoroutineScope()
        val snackbarState = remember { SnackbarHostState() }
        var isDateDialogShow by rememberSaveable { mutableStateOf(false) }
        val drawerManager = LocalDrawerManager.current
        val strings = HomeThemeRes.strings
        val navController = rememberNavController()

        Scaffold(
            content = { paddingValue ->
                Box(modifier = Modifier.padding(paddingValue)) {
                    NavHost(startDestination = HomePageRoute.HomePageMainScreen, navController = navController){
                        composable<HomePageRoute.HomePageMainScreen> {
                            HomeContent(
                                state = state,
                                modifier = Modifier,
                                onChangeDate = { date -> dispatchEvent(HomeEvent.LoadSchedule(date)) },
                                onTimeTaskEdit = { dispatchEvent(HomeEvent.PressEditTimeTaskButton(it)) },
                                onTaskDoneChange = { dispatchEvent(HomeEvent.ChangeTaskDoneStateButton(it)) },
                                onTimeTaskAdd = { start, end -> dispatchEvent(HomeEvent.PressAddTimeTaskButton(start, end)) },
                                onCreateSchedule = { dispatchEvent(HomeEvent.CreateSchedule) },
                                onTimeTaskIncrease = { dispatchEvent(HomeEvent.TimeTaskShiftUp(it)) },
                                onTimeTaskReduce = { dispatchEvent(HomeEvent.TimeTaskShiftDown(it)) },
                                onChangeToggleStatus = { dispatchEvent(HomeEvent.PressViewToggleButton(it)) },
                            )
                        }
                    }

                }
            },
            topBar = {
                  HomeTopAppBar(
                      calendarIconBehavior = state.calendarButtonBehavior,
                      onMenuIconClick = {
                          scope.launch { drawerManager?.openDrawer() }
                      },
                      onOverviewIconClick = {
                          dispatchEvent(HomeEvent.PressOverviewButton)
                      },
                      onOpenCalendar = {
                          isDateDialogShow = true
                      },
                      onGoToToday = {
                          dispatchEvent(HomeEvent.LoadSchedule(Date().startThisDay()))

                      }
                  )
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarState
                ) { snackbarData ->
                    ErrorSnackbar(snackbarData)
                }
            }
        )
        HomeDatePicker(
            isOpenDialog = isDateDialogShow,
            onDismiss = { isDateDialogShow = false },
            onSelectedDate = {
                isDateDialogShow = false
                dispatchEvent(HomeEvent.LoadSchedule(it))
            }
        )
        LaunchedEffect(Unit) {
            dispatchEvent(HomeEvent.LoadSchedule(state.currentDate))
        }
        handleEffect {
            when(it){
                is HomeEffect.NavigateToEditorCreator -> {
                    Log.d("VIEW MODEL","Editor Screen Called");
                    mainNavController.navigate(MainRoute.NavigateToEditorCreator(it.timeTask.serialize()))
                    //navController.navigate(HomePageRoute.EditorCreatorScreen(it.timeTask.serialize()))
                }
                is HomeEffect.ShowError -> {}
            }
        }
    }

}