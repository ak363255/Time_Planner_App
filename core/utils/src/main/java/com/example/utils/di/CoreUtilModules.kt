package com.example.utils.di

import com.example.utils.managers.CoroutineManager
import com.example.utils.managers.DateManager
import com.example.utils.managers.TimeOverlayManager
import org.koin.core.module.Module
import org.koin.dsl.module

object CoreUtilModules {
    private val coreUtilDep = module {
        single<CoroutineManager> { CoroutineManager.Base() }
        single<DateManager> {
            DateManager.Base()
        }
        single<TimeOverlayManager> {
            TimeOverlayManager.Base()
        }
    }

    fun provideCoreUtilDep() : List<Module> = listOf(coreUtilDep)
}