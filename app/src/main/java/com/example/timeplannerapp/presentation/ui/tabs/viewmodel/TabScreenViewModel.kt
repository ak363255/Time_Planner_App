package com.example.timeplannerapp.presentation.ui.tabs.viewmodel

import com.example.timeplannerapp.presentation.ui.tabs.contract.TabAction
import com.example.timeplannerapp.presentation.ui.tabs.contract.TabViewState
import com.example.timeplannerapp.presentation.ui.tabs.contract.TabsEffect
import com.example.timeplannerapp.presentation.ui.tabs.contract.TabsEvent
import com.example.timeplannerapp.presentation.ui.tabs.views.HomePageRoute
import com.example.timeplannerapp.presentation.ui.tabs.views.TabsBottomBarItems
import com.example.utils.managers.CoroutineManager
import com.example.utils.platform.communications.state.EffectCommunicator
import com.example.utils.platform.screenmodel.BaseViewModel
import com.example.utils.platform.screenmodel.EmptyDeps
import com.example.utils.platform.screenmodel.work.WorkScope

class TabScreenViewModel(
    communicator: TabStateCommunicator,
    coroutineManager: CoroutineManager
): BaseViewModel<TabViewState, TabsEvent, TabAction, TabsEffect, EmptyDeps>(
    stateCommunicator = communicator,
    effectCommunicator = EffectCommunicator.Empty(),
    coroutineManager = coroutineManager
){

    override fun init(deps: EmptyDeps) {
        if(!isInitialize.get()){
            super.init(deps)
            dispatchEvent(TabsEvent.Init)
        }
    }
    override suspend fun reduce(
        action: TabAction,
        currentState: TabViewState
    ): TabViewState = when(action){
              is TabAction.ChangeNavItems -> {
                  currentState.copy(bottomBarItem = action.item, route = action.route)
              }
          }

    override suspend fun WorkScope<TabViewState, TabAction, TabsEffect>.handleEvent(
        event: TabsEvent
    ) {
        when(event){
            TabsEvent.Init -> {
                navigate(TabsBottomBarItems.HOME, route = HomePageRoute.Home)
            }
            TabsEvent.SelectedAnalyticsTab -> {
                navigate(TabsBottomBarItems.ANALYTICS,route = HomePageRoute.Analytics)
            }
            TabsEvent.SelectedCategoriesScreen -> {
                navigate(TabsBottomBarItems.HOME, route = HomePageRoute.Categories)
            }
            TabsEvent.SelectedHomeTab -> {
                navigate(TabsBottomBarItems.HOME, route = HomePageRoute.Home)

            }
            TabsEvent.SelectedMainScreen -> {
                navigate(TabsBottomBarItems.HOME, route = HomePageRoute.Home)
            }
            TabsEvent.SelectedOverViewScreen -> {
                navigate(TabsBottomBarItems.HOME, route = HomePageRoute.Overview)
            }
            TabsEvent.SelectedSettingsTab -> {
                navigate(TabsBottomBarItems.SETTINGS, route = HomePageRoute.Setting)
            }
            TabsEvent.SelectedTemplateScreen -> {
                navigate(TabsBottomBarItems.HOME, route = HomePageRoute.Templates)
            }
        }

    }
    private suspend fun WorkScope<TabViewState, TabAction, TabsEffect>.navigate(
        bottomItem : TabsBottomBarItems,
        route: HomePageRoute
    ) = sendAction(TabAction.ChangeNavItems(item = bottomItem,route = route))

}