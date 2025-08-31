package com.example.impl.di.data_di

import com.example.domain.repository.TemplatesRepository
import com.example.impl.data.repositories.EditorRepositoryImpl
import com.example.impl.domain.repositories.EditorRepository
import org.koin.dsl.module

val editorRepositoryDependencies = module {
    single<EditorRepository> {
        EditorRepositoryImpl(get())
    }

}