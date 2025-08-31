package com.example.data.di

import com.example.data.datasources.categories.CategoriesLocalDataSource
import com.example.data.datasources.categories.MainCategoriesDao
import com.example.data.datasources.schedules.SchedulesDataBase
import com.example.data.datasources.subcategories.SubCategoriesDao
import com.example.data.datasources.subcategories.SubCategoriesLocalDataSource
import com.example.data.datasources.undefinedtasks.UndefinedTasksDao
import com.example.data.datasources.undefinedtasks.UndefinedTasksLocalDataSource
import com.example.data.repository.CategoriesRepositoryImpl
import com.example.data.repository.SubCategoriesRepositoryImpl
import com.example.data.repository.UndefinedTaskRepositoryImpl
import com.example.domain.repository.CategoriesRepository
import com.example.domain.repository.SubCategoriesRepository
import com.example.domain.repository.UndefinedTasksRepository
import org.koin.dsl.module

object CoreDataModule{
    private val coreDataDependencies = module {
        single<MainCategoriesDao> {
            get<SchedulesDataBase>().fetchMainCategoriesDao()
        }
        single<SubCategoriesDao> {
            get<SchedulesDataBase>().fetchSubCategoriesDao()
        }

        single<UndefinedTasksDao>{
            get<SchedulesDataBase>().fetchUndefinedTaskDao()
        }
        single<CategoriesLocalDataSource> {
            CategoriesLocalDataSource.Base(get())
        }
        single <SubCategoriesLocalDataSource>{
            SubCategoriesLocalDataSource.Base(get())
        }

        single<UndefinedTasksLocalDataSource> {
            UndefinedTasksLocalDataSource.Base(get())
        }

        single<CategoriesRepository> {
            CategoriesRepositoryImpl(get())
        }
        single<SubCategoriesRepository> {
            SubCategoriesRepositoryImpl(get())
        }
        single<UndefinedTasksRepository> {
            UndefinedTaskRepositoryImpl(get())
        }
    }

    fun provideDependencies() = listOf(coreDataDependencies)
}