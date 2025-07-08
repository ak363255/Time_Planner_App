package com.example.timeplannerapp.presentation.ui.tabs

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.timeplannerapp.presentation.ui.tabs.contract.TabViewState
import com.example.timeplannerapp.presentation.ui.tabs.contract.TabsEvent
import com.example.timeplannerapp.presentation.ui.tabs.viewmodel.TabScreenViewModel
import com.example.timeplannerapp.presentation.ui.tabs.views.HomePageDrawerItems
import com.example.timeplannerapp.presentation.ui.tabs.views.HomePageNavigationDrawer
import com.example.timeplannerapp.presentation.ui.tabs.views.TabsBottomBarItems
import com.example.timeplannerapp.presentation.ui.tabs.views.TabsBottomNavigationBar
import com.example.utils.managers.rememberDrawerManager
import com.example.utils.platform.screen.ScreenContent
import com.example.utils.platform.screenmodel.EmptyDeps


@Composable
fun TabScreen(
    modifier: Modifier = Modifier,
    tabViewModel: TabScreenViewModel,
    initialState: TabViewState = TabViewState()
) {
    ScreenContent(
        screenModel = tabViewModel,
        initialState = initialState,
        dependencies = EmptyDeps
    ) { state ->
        val state = fetchState()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val drawerManager = rememberDrawerManager(drawerState)
        Log.d("TAB","STATE ${state.bottomBarItem.name}")
        HomePageNavigationDrawer(
            drawerState = drawerState,
            drawerManager = drawerManager,
            isAlwaysSelected = state.bottomBarItem != TabsBottomBarItems.HOME,
            onItemSelected = { item ->
                val event = when (item) {
                    HomePageDrawerItems.MAIN -> TabsEvent.SelectedMainScreen
                    HomePageDrawerItems.OVERVIEW -> TabsEvent.SelectedOverViewScreen
                    HomePageDrawerItems.TEMPLATES -> TabsEvent.SelectedTemplateScreen
                    HomePageDrawerItems.CATEGORIES -> TabsEvent.SelectedCategoriesScreen
                }
                dispatchEvent(event)
            },
            content = {
                Scaffold(
                    content = { paddingValues ->

                        Box(modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()) {

                        }

                    },
                    bottomBar = {
                        TabsBottomNavigationBar(
                            selectedItem = state.bottomBarItem,
                            onItemSelected = {tab ->
                                val event = when(tab){
                                    TabsBottomBarItems.HOME -> TabsEvent.SelectedHomeTab
                                    TabsBottomBarItems.ANALYTICS -> TabsEvent.SelectedAnalyticsTab
                                    TabsBottomBarItems.SETTINGS -> TabsEvent.SelectedSettingsTab
                                }
                                dispatchEvent(event)
                            }
                        )
                    },


                    )
            }
        )

    }

}