package com.example.impl.presentation.ui.editor.contract

import com.example.domain.entities.categories.Categories
import com.example.impl.presentation.models.categories.CategoriesUi
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.impl.presentation.models.tasks.UndefinedTaskUi
import com.example.impl.presentation.models.template.TemplateUi
import com.example.impl.presentation.ui.editor.screenmodel.CategoryValidator
import com.example.impl.presentation.ui.editor.screenmodel.TimeRangeError
import com.example.utils.platform.screenmodel.contract.BaseAction


sealed class EditorAction : BaseAction{
    object Navigate : EditorAction()
    data class SetUp(val editModel : EditModelUi,val categories : List<CategoriesUi>): EditorAction()
    data class UpdateUndefinedTasks(val tasks : List<UndefinedTaskUi>) : EditorAction()
    data class UpdateCategories(val categories : List<CategoriesUi>) : EditorAction()
    data class UpdateTemplates(val templates : List<TemplateUi>): EditorAction()
    data class UpdateTemplateId(val templateId : Int?): EditorAction()
    data class UpdateEditModel(val editModel: EditModelUi): EditorAction()
    data class SetValidError(val timeRange : TimeRangeError?, val categoriesUi: CategoryValidator?): EditorAction()

}