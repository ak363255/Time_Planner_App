package com.example.impl.di

import com.example.impl.di.home_data_di.HomeDataComponents
import com.example.impl.di.home_domain_di.HomeDomainComponents
import com.example.impl.di.home_presentation_di.HomePresentationComponents
import org.koin.core.module.Module

object HomeModules {
    fun provideHomeDep(): List<Module> = listOf(
        HomeDataComponents.homeFeatureDataDependencies,
        HomeDomainComponents.homeFeatureDomainDi,
        HomePresentationComponents.homeFeaturePresentationDi
    )
}