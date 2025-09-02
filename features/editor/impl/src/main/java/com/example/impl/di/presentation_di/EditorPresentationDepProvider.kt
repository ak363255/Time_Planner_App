package com.example.impl.di.presentation_di

import com.example.domain.entities.schedules.TimeTask
import com.example.impl.presentation.ui.editor.processors.EditorWorkProcessor
import com.example.impl.presentation.ui.editor.processors.TimeTaskWorkProcessor
import com.example.impl.presentation.ui.editor.screenmodel.CategoryValidateError
import com.example.impl.presentation.ui.editor.screenmodel.CategoryValidator
import com.example.impl.presentation.ui.editor.screenmodel.EditorEffectCommunicator
import com.example.impl.presentation.ui.editor.screenmodel.EditorScreenModel
import com.example.impl.presentation.ui.editor.screenmodel.EditorStateCommunicator
import com.example.impl.presentation.ui.editor.screenmodel.TimeRangeValidator
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import kotlin.math.sin

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

    single<EditorStateCommunicator> {
        EditorStateCommunicator.Base()
    }
    single<EditorEffectCommunicator> {
        EditorEffectCommunicator.Base()
    }

    single<TimeRangeValidator> {
        TimeRangeValidator.Base()
    }
    single<CategoryValidator> {
        CategoryValidator.Base()
    }
    viewModelOf(::EditorScreenModel)
}