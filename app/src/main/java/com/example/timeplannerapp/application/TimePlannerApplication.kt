package com.example.timeplannerapp.application

import com.example.impl.di.HomeModules
import com.example.impl.di.SettingModules
import com.example.timeplannerapp.di.modules.AppModules
import com.example.utils.di.CoreUtilModules
import com.example.utils.platform.services.AnalyticsService
import com.example.utils.platform.services.BaseApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TimePlannerApplication : BaseApplication() {

    override lateinit var analyticsService: AnalyticsService
    override fun initDi() {
        startKoin {
            androidContext(this@TimePlannerApplication)
            modules(
                CoreUtilModules.provideCoreUtilDep() +
                        AppModules.provideAppModules() +
                        HomeModules.provideHomeDep() +
                        SettingModules.provideSettingDep()
            )
        }
    }

    override fun initPlatformService() {
    }

    override fun initSettings() {

    }

}