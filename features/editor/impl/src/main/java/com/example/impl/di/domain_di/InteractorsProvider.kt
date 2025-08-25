package com.example.impl.di.domain_di

import com.example.impl.domain.interactors.EditorInteractor
import org.koin.dsl.module

val editorInteractorsDependency = module {
    single<EditorInteractor> {
        EditorInteractor.Base(get())
    }
}