package com.example.impl.presentation.ui.editor.screenmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.entities.schedules.TimeTask
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.impl.presentation.ui.editor.contract.EditorAction
import com.example.impl.presentation.ui.editor.contract.EditorEffect
import com.example.impl.presentation.ui.editor.contract.EditorEvent
import com.example.impl.presentation.ui.editor.contract.EditorViewState
import com.example.impl.presentation.ui.editor.processors.EditorWorkCommand
import com.example.impl.presentation.ui.editor.processors.EditorWorkProcessor
import com.example.impl.presentation.ui.editor.processors.TimeTaskWorkCommand
import com.example.impl.presentation.ui.editor.processors.TimeTaskWorkProcessor
import com.example.presentation.ui.contract.MainRoute
import com.example.utils.extensions.duration
import com.example.utils.functional.deserialize
import com.example.utils.managers.CoroutineManager
import com.example.utils.platform.screenmodel.BaseViewModel
import com.example.utils.platform.screenmodel.EmptyDeps
import com.example.utils.platform.screenmodel.work.BackgroundWorkKey
import com.example.utils.platform.screenmodel.work.WorkScope
import kotlinx.coroutines.launch

class EditorScreenModel(
 private val timeTaskWorkProcessor: TimeTaskWorkProcessor,
    private val editorWorkProcessor: EditorWorkProcessor,
    private val timeRangeValidator : TimeRangeValidator,
    private val categoryValidator : CategoryValidator,
    private val saveStateHandle : SavedStateHandle,
    stateCommunicator : EditorStateCommunicator,
    effectCommunicator : EditorEffectCommunicator,
    coroutineManager: CoroutineManager
) : BaseViewModel<EditorViewState, EditorEvent, EditorAction, EditorEffect, EmptyDeps>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager
){
    init {
        readEditModel()
    }

    private fun readEditModel(){
        viewModelScope.launch {
            val page = saveStateHandle.toRoute<MainRoute.NavigateToEditorCreator>()
            if(page.timeTask.isNotEmpty()){
                val timeTask = deserialize<TimeTask>( saveStateHandle.toRoute<MainRoute.NavigateToEditorCreator>().timeTask)
                editorWorkProcessor.work(EditorWorkCommand.SaveSendEditModel(timeTask))
            }
        }
    }
    override fun init(deps: EmptyDeps) {
        if(!isInitialize.get()){
            super.init(deps)
            dispatchEvent(EditorEvent.Init)
        }
    }


    override suspend fun reduce(
        action: EditorAction,
        currentState: EditorViewState
    ): EditorViewState = when(action){
        EditorAction.Navigate -> currentState.copy()
        is EditorAction.SetUp -> currentState.copy(
            editModel = action.editModel,
            categories = action.categories,
            timeRangeValid = null,
            categoryValid = null
        )
        is EditorAction.SetValidError -> currentState.copy(
            timeRangeValid = action.timeRange,
            categoryValid = action.categoriesUi
        )
        is EditorAction.UpdateCategories -> currentState.copy(
            categories = action.categories
        )
        is EditorAction.UpdateEditModel -> currentState.copy(
            editModel = action.editModel
        )
        is EditorAction.UpdateTemplateId -> currentState.copy(
            editModel = currentState.editModel?.copy(templateId = action.templateId)
        )
        is EditorAction.UpdateTemplates -> currentState.copy(templates = action.templates)
        is EditorAction.UpdateUndefinedTasks -> currentState.copy(
            undefinedTasks = action.tasks
        )
    }

    override suspend fun WorkScope<EditorViewState, EditorAction, EditorEffect>.handleEvent(
        event: EditorEvent
    ) {
        when(event){
            is EditorEvent.AddSubCategory -> launchBackgroundWork(BackgroundKey.DATA_ACTION){
                val mainCategory = checkNotNull(state().editModel?.mainCategory)
                val command = EditorWorkCommand.AddSubCategory(name = event.name,mainCategory)
                editorWorkProcessor.work(command).handleWork()
            }
            is EditorEvent.ApplyTemplate -> launchBackgroundWork(BackgroundKey.DATA_ACTION){
                val command = EditorWorkCommand.ApplyTemplate(event.template,checkNotNull(state().editModel))
               editorWorkProcessor.work(command).handleWork()
            }
            is EditorEvent.ApplyUndefinedTask -> launchBackgroundWork(BackgroundKey.DATA_ACTION){
                val command = EditorWorkCommand.ApplyUndefinedTask(event.task,checkNotNull(state().editModel))
               editorWorkProcessor.work(command).handleWork()
            }
            is EditorEvent.ChangeCategories -> updateEditModel {
                copy(mainCategory = event.category, subCategory = event.subCategories)
            }
            is EditorEvent.ChangeNote -> updateEditModel {
                copy(note = event.note)
            }
            is EditorEvent.ChangeParameters -> updateEditModel {
                copy(parameters = event.parameter)
            }
            is EditorEvent.ChangeTime -> updateEditModel{
                copy(timeRange = event.timeRange, duration = duration(event.timeRange))
            }
            EditorEvent.CreateTemplate -> launchBackgroundWork(BackgroundKey.DATA_ACTION){
                val command = EditorWorkCommand.AddTemplate(checkNotNull(state().editModel))
                editorWorkProcessor.work(command).handleWork()
            }
            EditorEvent.Init -> {
                val command = EditorWorkCommand.LoadSendEditModel
                editorWorkProcessor.work(command).handleWork()
                launchBackgroundWork(BackgroundKey.LOAD_TEMPLATES){
                    val templatesCommand = EditorWorkCommand.LoadTemplates
                    editorWorkProcessor.work(templatesCommand).handleWork()
                }
                launchBackgroundWork(BackgroundKey.LOAD_UNDEFINED_TASKS){
                    val taskCommand = TimeTaskWorkCommand.LoadUndefinedTasks
                    timeTaskWorkProcessor.work(taskCommand).handleWork()
                }
            }
            is EditorEvent.NavigateToCategoryEditor -> {}
            is EditorEvent.NavigateToSubCategoryEditor -> {}
            EditorEvent.PressBackButton -> {}
            EditorEvent.PressControlTemplateButton -> {}
            EditorEvent.PressDeleteButton -> launchBackgroundWork(BackgroundKey.DELETE_MODEL){
                val command = TimeTaskWorkCommand.DeleteModel(checkNotNull(state().editModel))
                timeTaskWorkProcessor.work(command).handleWork()
            }
            EditorEvent.PressSaveButton -> launchBackgroundWork(BackgroundKey.SAVE_MODEL){
                with(checkNotNull(state().editModel)){
                    val timeValidate = timeRangeValidator.validate(timeRange)
                    val categoryValidate = categoryValidator.validate(mainCategory)
                    if(timeValidate.isValid && categoryValidate.isValid){
                        val command = TimeTaskWorkCommand.AddOrSaveModel(this)
                        timeTaskWorkProcessor.work(command).handleWork()
                    }else{
                        val action = EditorAction.SetValidError(timeValidate.validError,categoryValidate.validError)
                        sendAction(action)
                    }
                }
            }
        }
    }

    private suspend fun WorkScope<EditorViewState, EditorAction, EditorEffect>.updateEditModel(onTransform : EditModelUi.()-> EditModelUi){
        val editModel = checkNotNull(state().editModel)
        sendAction(EditorAction.UpdateEditModel(onTransform(editModel)))
    }

    enum class BackgroundKey : BackgroundWorkKey {
        LOAD_TEMPLATES, LOAD_UNDEFINED_TASKS, SAVE_MODEL, DELETE_MODEL, DATA_ACTION
    }

}