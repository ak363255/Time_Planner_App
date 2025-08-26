package com.example.impl.presentation.ui.editor.processors

import com.example.impl.domain.entities.EditModel
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.utils.platform.screenmodel.work.WorkCommand
import com.example.utils.platform.screenmodel.work.WorkProcessor

interface TimeTaskWorkProcessor : WorkProcessor<TimeTaskWorkCommand>{
}

sealed class TimeTaskWorkCommand : WorkCommand{
    object LoadUndefinedTasks : TimeTaskWorkCommand()
    data class AddOrSaveModel(val editModel : EditModelUi): TimeTaskWorkCommand()
    data class DeleteModel(val editModel : EditModelUi) : TimeTaskWorkCommand()
}