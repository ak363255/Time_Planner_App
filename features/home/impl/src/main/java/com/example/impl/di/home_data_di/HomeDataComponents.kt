package com.example.impl.di.home_data_di

import com.example.impl.data.datasource.FeatureScheduleLocalDataSource
import com.example.impl.data.repositories.FeatureScheduleRepositoryImpl
import com.example.impl.domain.repository.FeatureScheduleRepository
import org.koin.dsl.module

internal object HomeDataComponents {
    val homeFeatureDataDependencies = module {

        single<FeatureScheduleLocalDataSource> {
            FeatureScheduleLocalDataSource.Base()
        }
        single<FeatureScheduleRepository> {
            FeatureScheduleRepositoryImpl(
                localDataSource = get()
            )
        }




    }

}