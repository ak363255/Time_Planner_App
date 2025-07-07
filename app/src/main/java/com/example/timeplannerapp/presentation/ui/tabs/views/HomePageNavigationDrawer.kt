package com.example.timeplannerapp.presentation.ui.tabs.views

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.theme.TimePlannerRes
import com.example.presentation.ui.views.DrawerItems
import com.example.presentation.ui.views.DrawerLogoSection
import com.example.utils.managers.DrawerItem
import com.example.utils.managers.DrawerManager
import com.example.utils.managers.LocalDrawerManager
import kotlinx.coroutines.launch


@Composable
fun HomePageNavigationDrawer(
    drawerState: DrawerState,
    drawerManager: DrawerManager,
    content: @Composable () -> Unit,
    isAlwaysSelected : Boolean,
    onItemSelected : (HomePageDrawerItems) -> Unit,
) {
    val selectedItem by drawerManager.selectedItem.collectAsState(0)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                DrawerLogoSection(
                    logoIcon = painterResource(id = TimePlannerRes.icons.logo),
                    description = TimePlannerRes.strings.appName
                )
                DrawerItems(
                    modifier = Modifier.width(300.dp),
                    selectedItemIndex = selectedItem,
                    items = HomePageDrawerItems.values(),
                    isAlwaysSelected = isAlwaysSelected,
                    onItemSelected = {item ->
                        onItemSelected(item)
                        scope.launch { drawerManager.closeDrawer() }

                    }
                )
            }
        },
    ) {
        CompositionLocalProvider(
            LocalDrawerManager provides drawerManager,
            content = content
        )
    }

}

enum class HomePageDrawerItems : DrawerItem {
    MAIN {
        override val icon: Int
            @Composable
            get() = TimePlannerRes.icons.schedulerIcon
        override val title: String
            @Composable
            get() = TimePlannerRes.strings.mainDrawerTitle
    },
    OVERVIEW {
        override val icon: Int
            @Composable
            get() = TimePlannerRes.icons.overview
        override val title: String
            @Composable
            get() = TimePlannerRes.strings.overviewDrawerTitle
    },
    TEMPLATES {
        override val icon: Int
            @Composable
            get() = TimePlannerRes.icons.template
        override val title: String
            @Composable
            get() = TimePlannerRes.strings.templateDrawerTitle
    },
    CATEGORIES {
        override val icon: Int
            @Composable
            get() = TimePlannerRes.icons.categoriesIcon
        override val title: String
            @Composable
            get() = TimePlannerRes.strings.categoriesDrawerTitle
    },

}