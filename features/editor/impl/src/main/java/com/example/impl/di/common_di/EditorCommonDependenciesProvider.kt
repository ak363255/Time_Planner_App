package com.example.impl.di.common_di

import com.example.impl.domain.common.editor.EditorEitherWrapper
import com.example.impl.domain.common.editor.EditorErrorHandler
import org.koin.dsl.module

val editorCommonDependencies = module {
    single<EditorErrorHandler> {
        EditorErrorHandler.Base()
    }
    single<EditorEitherWrapper> {
        EditorEitherWrapper.Base(get())
    }
}