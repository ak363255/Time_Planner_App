package com.example.impl.di.presentation_di

import com.example.domain.entities.schedules.TimeTask
import com.example.impl.presentation.ui.editor.processors.EditorWorkProcessor
import com.example.impl.presentation.ui.editor.processors.TimeTaskWorkProcessor
import org.koin.dsl.module

val editorPresentationProvider = module {
    single<EditorWorkProcessor> {
        EditorWorkProcessor.Base(
            editorInteractor = get(),
            categoriesInteractor = get(),
            templatesInteractor = get()
        )
    }
    single<TimeTaskWorkProcessor> {
        TimeTaskWorkProcessor.Base(
            timeTaskInteractor = get(),
            undefinedTasksInteractor = get(),
            timeTaskAlarmManager = get()
        )
    }
}