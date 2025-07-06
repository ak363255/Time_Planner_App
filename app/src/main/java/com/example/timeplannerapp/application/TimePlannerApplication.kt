package com.example.timeplannerapp.application

import com.example.timeplannerapp.di.modules.DataBaseModule
import com.example.timeplannerapp.di.modules.ViewModelModule
import com.example.utils.platform.services.AnalyticsService
import com.example.utils.platform.services.BaseApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TimePlannerApplication : BaseApplication() {

    override lateinit var analyticsService: AnalyticsService
    override fun initDi() {
        startKoin {
            androidContext(this@TimePlannerApplication)
            modules(DataBaseModule.databaseModule, ViewModelModule.module)
        }
    }

    override fun initPlatformService() {
    }

    override fun initSettings() {

    }

}