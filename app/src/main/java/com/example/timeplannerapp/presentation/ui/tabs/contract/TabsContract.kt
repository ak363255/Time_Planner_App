package com.example.timeplannerapp.presentation.ui.tabs.contract

import com.example.timeplannerapp.presentation.ui.tabs.views.HomePageRoute
import com.example.timeplannerapp.presentation.ui.tabs.views.TabsBottomBarItems
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseViewState
import com.example.utils.platform.screenmodel.contract.EmptyUiEffect
import kotlinx.parcelize.Parcelize

@Parcelize
data class TabViewState(
    val bottomBarItem: TabsBottomBarItems = TabsBottomBarItems.HOME,
    val route : HomePageRoute = HomePageRoute.Home
): BaseViewState

sealed class TabsEvent : BaseEvent{
    object Init:TabsEvent()
    object SelectedHomeTab: TabsEvent()
    object SelectedOverViewScreen : TabsEvent()
    object SelectedMainScreen : TabsEvent()
    object SelectedTemplateScreen : TabsEvent()
    object SelectedCategoriesScreen : TabsEvent()
    object SelectedAnalyticsTab : TabsEvent()
    object SelectedSettingsTab : TabsEvent()
}
sealed class TabsEffect : EmptyUiEffect
sealed class TabAction : TabsEffect(), BaseAction{
    data class ChangeNavItems(val item : TabsBottomBarItems,val route: HomePageRoute): TabAction()
}