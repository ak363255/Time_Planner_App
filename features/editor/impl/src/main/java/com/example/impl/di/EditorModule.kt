package com.example.impl.di

import com.example.impl.di.data_di.editorRepositoryDependencies
import com.example.impl.di.domain_di.editorInteractorsDependency

object EditorModule {
    fun provideDependencies() = listOf(
        editorInteractorsDependency,
        editorRepositoryDependencies,
        editorInteractorsDependency
    )
}