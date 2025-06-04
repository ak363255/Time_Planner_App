package com.example.utils.manager

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
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

    }
}