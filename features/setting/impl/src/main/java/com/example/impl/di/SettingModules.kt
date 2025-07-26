package com.example.impl.di

import com.example.impl.di.data_di.SettingDataModule
import com.example.impl.di.domain_di.SettingDomainModules
import com.example.impl.di.presentation_di.SettingPresentationModule
import org.koin.core.module.Module

object SettingModules {
    fun provideSettingDep() : List<Module> = listOf(
        SettingDataModule.settingDataDep,
        SettingDomainModules.settingDomainDep,
        SettingPresentationModule.settingPresentationDep
    )
}