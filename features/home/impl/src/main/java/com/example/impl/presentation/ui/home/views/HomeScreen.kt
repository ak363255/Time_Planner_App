package com.example.impl.presentation.ui.home.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import com.example.impl.presentation.theme.HomeThemeRes
import com.example.impl.presentation.ui.home.contract.HomeEvent
import com.example.impl.presentation.ui.home.contract.HomeViewState
import com.example.impl.presentation.ui.home.screenModel.HomeScreenModel
import com.example.presentation.ui.views.ErrorSnackbar
import com.example.utils.extensions.startThisDay
import com.example.utils.managers.LocalDrawerManager
import com.example.utils.platform.screen.ScreenContent
import com.example.utils.platform.screenmodel.EmptyDeps
import kotlinx.coroutines.launch
import java.util.Date
import androidx.compose.runtime.setValue


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
        val scope = rememberCoroutineScope()
        val snackbarState = remember { SnackbarHostState() }
        var isDateDialogShow by rememberSaveable { mutableStateOf(false) }
        val drawerManager = LocalDrawerManager.current
        val strings = HomeThemeRes.strings

        Scaffold(
            content = { paddingValue ->
                Box(modifier = Modifier.padding(paddingValue)) {
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
    }

}