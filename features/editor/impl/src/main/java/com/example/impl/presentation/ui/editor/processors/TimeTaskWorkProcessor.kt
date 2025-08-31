package com.example.impl.presentation.ui.editor.processors

import com.example.impl.domain.common.editor.convertToTimeTask
import com.example.impl.domain.entities.EditorFailures
import com.example.impl.domain.interactors.editor.TimeTaskInteractor
import com.example.impl.domain.interactors.editor.UndefinedTasksInteractor
import com.example.impl.presentation.mappers.mapToDomain
import com.example.impl.presentation.mappers.mapToUi
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.impl.presentation.ui.editor.contract.EditorAction
import com.example.impl.presentation.ui.editor.contract.EditorEffect
import com.example.presentation.ui.notifications.TimeTaskAlarmManager
import com.example.utils.functional.Either
import com.example.utils.platform.screenmodel.work.ActionResult
import com.example.utils.platform.screenmodel.work.EffectResult
import com.example.utils.platform.screenmodel.work.WorkCommand
import com.example.utils.platform.screenmodel.work.WorkProcessor
import com.example.utils.platform.screenmodel.work.WorkResult

internal interface TimeTaskWorkProcessor : WorkProcessor<TimeTaskWorkCommand, EditorAction, EditorEffect>{
    class Base(
        private val timeTaskInteractor : TimeTaskInteractor,
        private val undefinedTasksInteractor : UndefinedTasksInteractor,
        private val timeTaskAlarmManager: TimeTaskAlarmManager,
    ): TimeTaskWorkProcessor{
        override suspend fun work(command: TimeTaskWorkCommand): WorkResult<EditorAction, EditorEffect> = when(command){
            is TimeTaskWorkCommand.AddOrSaveModel -> saveOrAddModelWork(command.editModel)
            is TimeTaskWorkCommand.DeleteModel -> deleteModelWork(command.editModel)
            is TimeTaskWorkCommand.LoadUndefinedTasks -> loadUndefinedTaskWork()
        }

        private suspend fun loadUndefinedTaskWork(): WorkResult<EditorAction, EditorEffect>{
            return when(val tasks = undefinedTasksInteractor.fetchAllUndefinedTasks()){
                is Either.Right -> ActionResult(
                    EditorAction.UpdateUndefinedTasks(tasks.data.map { it.mapToUi() })
                )
                is Either.Left -> EffectResult(
                    EditorEffect.ShowError(tasks.data)
                )
            }
        }

        private suspend fun saveOrAddModelWork(editModel: EditModelUi): WorkResult<EditorAction, EditorEffect>{
            val domainModel = editModel.mapToDomain()
            val timeTask = domainModel.convertToTimeTask()
            val saveResult = when(timeTask.key != 0L){
                true ->timeTaskInteractor.updateTimeTask(timeTask)
                false -> timeTaskInteractor.addTimeTask(timeTask)
            }
            return if(!editModel.checkDateIsRepeat()){
                when(saveResult){

                    is Either.Right -> {
                        if (editModel.undefinedTaskId != null) {
                            undefinedTasksInteractor.deleteUndefinedTask(editModel.undefinedTaskId)
                        }
                        //TODO
                        ActionResult(EditorAction.Navigate)
                    }
                    is Either.Left -> with(saveResult.data){
                        val effect = when(this is EditorFailures.TimeOverlayError){
                            true -> EditorEffect.ShowOverlayError(editModel.timeRange,this)
                            false -> EditorEffect.ShowError(this)
                        }
                        EffectResult(effect)
                    }
                }
            }else{
                when(saveResult){
                    //TODO
                    is Either.Right -> ActionResult(EditorAction.Navigate)

                    is Either.Left -> EffectResult(EditorEffect.ShowError(saveResult.data))
                }
            }
        }

        private suspend fun deleteModelWork(editModel: EditModelUi): WorkResult<EditorAction, EditorEffect>{
            val domainModel = editModel.mapToDomain()
            if(domainModel.key != 0L){
                val deleteResult = timeTaskInteractor.deleteTimeTask(key = domainModel.key)
                if(deleteResult is Either.Left){
                    return EffectResult(EditorEffect.ShowError(deleteResult.data))
                }else{
                    timeTaskAlarmManager.deleteNotifyAlarm(domainModel.convertToTimeTask())
                }
            }
            return ActionResult(EditorAction.Navigate)
        }

    }

}

internal sealed class TimeTaskWorkCommand : WorkCommand{
    internal object LoadUndefinedTasks : TimeTaskWorkCommand()
    internal data class AddOrSaveModel(val editModel : EditModelUi): TimeTaskWorkCommand()
    internal data class DeleteModel(val editModel : EditModelUi) : TimeTaskWorkCommand()
}