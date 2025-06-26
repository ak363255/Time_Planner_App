package com.example.utils.platform.services

import android.app.Application

abstract class BaseApplication : Application() {

    abstract var analyticsService : AnalyticsService
    abstract fun initDi()
    abstract fun initPlatformService()
    abstract fun initSettings()
    override fun onCreate() {
        super.onCreate()
        initDi()
        initPlatformService()
        initSettings()
    }

}