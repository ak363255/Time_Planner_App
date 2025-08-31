package com.example.impl.di.data_di

import com.example.impl.data.datasources.EditorLocalDataSource
import org.koin.dsl.module

val editorLocalDataSourcesDependencies = module {
    single<EditorLocalDataSource> {
        EditorLocalDataSource.Base()
    }
}