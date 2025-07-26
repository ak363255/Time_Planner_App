package com.example.impl.di.domain_di

import com.example.impl.domain.common.SettingsEitherWrapper
import com.example.impl.domain.common.SettingsErrorHandler
import org.koin.dsl.module

object SettingDomainModules {
    val settingDomainDep = module {
        single<SettingsErrorHandler> { SettingsErrorHandler() }
        single<SettingsEitherWrapper> { SettingsEitherWrapper.Base(errorHandler = get()) }
    }
}