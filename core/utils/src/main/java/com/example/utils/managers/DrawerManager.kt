package com.example.utils.managers

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow

interface DrawerManager {
    suspend fun openDrawer()
    suspend fun closeDrawer()
    val drawerValue : State<DrawerValue>
    val selectedItem : MutableStateFlow<Int>

    class Base(private val drawerState: DrawerState) : DrawerManager{
        override suspend fun openDrawer() {
            drawerState.open()
        }
        override suspend fun closeDrawer() {
            drawerState.close()
        }
        override val drawerValue: State<DrawerValue> = derivedStateOf { drawerState.currentValue }
        override val selectedItem: MutableStateFlow<Int> = MutableStateFlow(0)
        companion object{
            fun Saver(drawerState: DrawerState) = androidx.compose.runtime.saveable.Saver<Base, Any>(
                save = {null},
                restore = {Base(drawerState)}
            )
        }
    }

}

val LocalDrawerManager = staticCompositionLocalOf<DrawerManager?>{ null }
@Composable
fun rememberDrawerManager(drawerState: DrawerState): DrawerManager{
    return rememberSaveable(saver = DrawerManager.Base.Saver(drawerState)) {
        DrawerManager.Base(drawerState)
    }
}

interface DrawerItem{
    val icon : Int @Composable get
    val title : String @Composable get
}