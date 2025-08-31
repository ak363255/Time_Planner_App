package com.example.impl.di.domain_di

import com.example.impl.domain.interactors.editor.CategoriesInteractor
import com.example.impl.domain.interactors.editor.EditorInteractor
import com.example.impl.domain.interactors.editor.TemplatesInteractor
import com.example.impl.domain.interactors.editor.TimeTaskInteractor
import com.example.impl.domain.interactors.editor.UndefinedTasksInteractor
import org.koin.dsl.module

val editorInteractorsDependency = module {
    single<EditorInteractor> {
        EditorInteractor.Base(get())
    }
    single<CategoriesInteractor> {
        CategoriesInteractor.Base(
            categoriesRepository = get(),
            subCategoriesRepository = get(),
            eitherWrapper = get()
        )
    }

    single<TemplatesInteractor> {
        TemplatesInteractor.Base(eitherWrapper = get(), templatesRepository = get())
    }

    single<TimeTaskInteractor> {
        TimeTaskInteractor.Base(
            scheduleRepository = get(),
            timeTaskRepository = get(),
            statusChecker = get(),
            overlayManager = get(),
            dateManager = get(),
            eitherWrapper = get()
        )
    }
    single<UndefinedTasksInteractor> {
        UndefinedTasksInteractor.Base(
            undefinedTaskRepository = get(),
            eitherWrapper = get()
        )
    }

}