package com.example.timeplannerapp.presentation.ui.tabs.views

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.theme.TimePlannerRes
import com.example.presentation.ui.views.BottomBarItem
import com.example.presentation.ui.views.BottomNavigationBar

@Composable
fun TabsBottomNavigationBar(
    modifier : Modifier = Modifier,
    selectedItem: TabsBottomBarItems,
    onItemSelected : (TabsBottomBarItems) -> Unit
){
    BottomNavigationBar(
        modifier = modifier.height(80.dp),
        selectedItem = selectedItem,
        items = TabsBottomBarItems.values(),
        showLabel = true,
        onItemSelected = onItemSelected
    )
}

enum class TabsBottomBarItems : BottomBarItem{
    HOME{
        override val label: String @Composable get() = TimePlannerRes.strings.homeTabTitle
        override val enabledIcon: Int @Composable get() = TimePlannerRes.icons.enabledHomeIcon
        override val disabledIcon: Int @Composable get() = TimePlannerRes.icons.disabledHomeIcon
    },
    ANALYTICS{
        override val label: String @Composable get() = TimePlannerRes.strings.homeTabTitle
        override val enabledIcon: Int @Composable get() = TimePlannerRes.icons.enabledHomeIcon
        override val disabledIcon: Int @Composable get() = TimePlannerRes.icons.disabledHomeIcon
    },
    SETTINGS{
        override val label: String @Composable get() = TimePlannerRes.strings.homeTabTitle
        override val enabledIcon: Int @Composable get() = TimePlannerRes.icons.enabledHomeIcon
        override val disabledIcon: Int @Composable get() = TimePlannerRes.icons.disabledHomeIcon
    }

}