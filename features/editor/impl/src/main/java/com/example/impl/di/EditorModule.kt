package com.example.impl.di

import com.example.impl.di.common_di.editorCommonDependencies
import com.example.impl.di.data_di.editorRepositoryDependencies
import com.example.impl.di.domain_di.editorInteractorsDependency
import com.example.impl.di.presentation_di.editorPresentationProvider

object EditorModule {
    fun provideDependencies() = listOf(
        editorInteractorsDependency,
        editorRepositoryDependencies,
        editorPresentationProvider,
        editorCommonDependencies
    )
}