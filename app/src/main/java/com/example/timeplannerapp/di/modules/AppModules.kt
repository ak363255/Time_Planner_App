package com.example.timeplannerapp.di.modules

import com.example.timeplannerapp.di.modules.data_module.AppDataModule
import com.example.timeplannerapp.di.modules.database_module.AppDataBaseModule
import com.example.timeplannerapp.di.modules.domain_module.AppDomainModule
import com.example.timeplannerapp.di.modules.presentation_module.AppPresentationModule
import org.koin.core.module.Module

object AppModules {
    fun provideAppModules(): List<Module> = listOf(
        AppDataModule.appDataDep,
        AppDomainModule.appDomainDep,
        AppPresentationModule.appUiDep,
        AppDataBaseModule.appDatabaseDep
    )
}