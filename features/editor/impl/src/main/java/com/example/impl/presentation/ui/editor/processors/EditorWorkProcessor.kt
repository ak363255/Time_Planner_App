package com.example.impl.presentation.ui.editor.processors

import com.example.impl.domain.common.editor.convertToEditModel
import com.example.impl.domain.common.editor.convertToTemplate
import com.example.impl.domain.interactors.editor.CategoriesInteractor
import com.example.impl.domain.interactors.editor.EditorInteractor
import com.example.impl.domain.interactors.editor.TemplatesInteractor
import com.example.impl.presentation.mappers.mapToDomain
import com.example.impl.presentation.mappers.mapToUi
import com.example.impl.presentation.models.categories.editor.MainCategoryUi
import com.example.impl.presentation.models.categories.editor.SubCategoryUi
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.impl.presentation.models.tasks.UndefinedTaskUi
import com.example.impl.presentation.models.tasks.convertToEditModel
import com.example.impl.presentation.models.template.TemplateUi
import com.example.impl.presentation.ui.editor.contract.EditorAction
import com.example.impl.presentation.ui.editor.contract.EditorEffect
import com.example.utils.functional.Either
import com.example.utils.functional.rightOrElse
import com.example.utils.platform.screenmodel.work.ActionResult
import com.example.utils.platform.screenmodel.work.EffectResult
import com.example.utils.platform.screenmodel.work.WorkCommand
import com.example.utils.platform.screenmodel.work.WorkProcessor
import com.example.utils.platform.screenmodel.work.WorkResult
import java.util.Date

internal interface EditorWorkProcessor : WorkProcessor<EditorWorkCommand, EditorAction, EditorEffect>{
    class Base(
        private val editorInteractor: EditorInteractor,
        private val categoriesInteractor: CategoriesInteractor,
        private val templatesInteractor : TemplatesInteractor
    ): EditorWorkProcessor{
        override suspend fun work(command: EditorWorkCommand): WorkResult<EditorAction, EditorEffect> = when(command){
            is EditorWorkCommand.AddSubCategory -> addSubCategoryWork(command.name,command.mainCategory)
            is EditorWorkCommand.AddTemplate -> addTemplateWork(command.editModel)
            is EditorWorkCommand.ApplyTemplate -> applyTemplateWork(command.template,command.model)
            is EditorWorkCommand.ApplyUndefinedTask -> applyUndefinedTaskWork(command.task,command.model)
            EditorWorkCommand.LoadSendEditModel -> loadSendModelWork()
            EditorWorkCommand.LoadTemplates -> loadTemplatesWork()
        }
        private suspend fun loadSendModelWork(): WorkResult<EditorAction, EditorEffect> {
            val editModel = editorInteractor.fetchEditModel().mapToUi()
            return when (val result = categoriesInteractor.fetchCategories()) {
                is Either.Right -> ActionResult(
                    EditorAction.SetUp(editModel,result.data.map { it.mapToUi() })
                )
                is Either.Left -> EffectResult(EditorEffect.ShowError(result.data))
            }
        }

        private suspend fun loadTemplatesWork(): WorkResult<EditorAction, EditorEffect> {
            return when (val templates = templatesInteractor.fetchTemplates()) {
                is Either.Right -> ActionResult(EditorAction.UpdateTemplates(templates.data.map { it.mapToUi() }))
                is Either.Left -> EffectResult(EditorEffect.ShowError(templates.data))
            }
        }

        private suspend fun addSubCategoryWork(
            name: String,
            mainCategory: MainCategoryUi,
        ): WorkResult<EditorAction, EditorEffect> {
            val subCategory = SubCategoryUi(id = 0, name = name, mainCategory = mainCategory)
            return when (val result = categoriesInteractor.addSubCategory(subCategory.mapToDomain())) {
                is Either.Right -> {
                    when (val categories = categoriesInteractor.fetchCategories()) {
                        is Either.Right -> ActionResult(EditorAction.UpdateCategories(categories.data.map { it.mapToUi() }))
                        is Either.Left -> EffectResult(EditorEffect.ShowError(categories.data))
                    }
                }
                is Either.Left -> EffectResult(EditorEffect.ShowError(result.data))
            }
        }

        private suspend fun addTemplateWork(editModel: EditModelUi): WorkResult<EditorAction, EditorEffect> {
            val template = editModel.mapToDomain().convertToTemplate()
            val templateId = templatesInteractor.addTemplate(template).rightOrElse(null)
            return ActionResult(EditorAction.UpdateTemplateId(templateId))
        }

        private fun applyTemplateWork(template: TemplateUi, model: EditModelUi): WorkResult<EditorAction, EditorEffect> {
            val domainModel = template.mapToDomain().convertToEditModel(model.date).copy(key = model.key)
            return ActionResult(EditorAction.UpdateEditModel(domainModel.mapToUi()))
        }

        private fun applyUndefinedTaskWork(task: UndefinedTaskUi, model: EditModelUi): WorkResult<EditorAction, EditorEffect> {
            val editModel = task.convertToEditModel(model.date, model.timeRange).copy(
                key = model.key,
                createdAt = Date(),
            )
            return ActionResult(EditorAction.UpdateEditModel(editModel))
        }




    }

}

sealed class EditorWorkCommand : WorkCommand{
    internal data object LoadSendEditModel : EditorWorkCommand()
    internal data object LoadTemplates : EditorWorkCommand()
    internal data class AddSubCategory(val name : String ,val mainCategory: MainCategoryUi):
        EditorWorkCommand()
    internal data class AddTemplate(val editModel : EditModelUi): EditorWorkCommand()
    internal data class ApplyTemplate(val template : TemplateUi,val model : EditModelUi): EditorWorkCommand()
    internal data class ApplyUndefinedTask(val task : UndefinedTaskUi, val model : EditModelUi):
        EditorWorkCommand()
}