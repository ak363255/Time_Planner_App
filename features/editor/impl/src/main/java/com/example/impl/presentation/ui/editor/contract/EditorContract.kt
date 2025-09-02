package com.example.impl.presentation.ui.editor.contract

import com.example.impl.domain.entities.EditorFailures
import com.example.impl.presentation.models.categories.editor.CategoriesUi
import com.example.impl.presentation.models.categories.editor.MainCategoryUi
import com.example.impl.presentation.models.categories.editor.SubCategoryUi
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.impl.presentation.models.editmodel.EditParameters
import com.example.impl.presentation.models.tasks.UndefinedTaskUi
import com.example.impl.presentation.models.template.TemplateUi
import com.example.impl.presentation.ui.editor.screenmodel.CategoryValidateError
import com.example.impl.presentation.ui.editor.screenmodel.TimeRangeError
import com.example.utils.functional.TimeRange
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.parcelize.Parcelize


sealed class EditorAction : BaseAction{
    object Navigate : EditorAction()
    internal data class SetUp(val editModel : EditModelUi,val categories : List<CategoriesUi>): EditorAction()
    internal data class UpdateUndefinedTasks(val tasks : List<UndefinedTaskUi>) : EditorAction()
   internal data class UpdateCategories(val categories : List<CategoriesUi>) : EditorAction()
   internal data class UpdateTemplates(val templates : List<TemplateUi>): EditorAction()
   internal data class UpdateTemplateId(val templateId : Int?): EditorAction()
    internal data class UpdateEditModel(val editModel: EditModelUi): EditorAction()
    internal data class SetValidError(val timeRange : TimeRangeError?, val categoriesUi: CategoryValidateError?): EditorAction()
}

sealed class EditorEffect : BaseUiEffect{
    data class ShowError(val failures : EditorFailures) : EditorEffect()
    data class ShowOverlayError(val currentTimeRange : TimeRange,val failures : EditorFailures.TimeOverlayError):
        EditorEffect()
}

sealed class EditorEvent : BaseEvent{
    internal data object Init : EditorEvent()
    internal data object CreateTemplate : EditorEvent()
    internal data class ApplyTemplate(val template : TemplateUi): EditorEvent()
    internal data class ApplyUndefinedTask(val task: UndefinedTaskUi): EditorEvent()
    internal data class ChangeTime(val timeRange: TimeRange): EditorEvent()
    internal data class ChangeCategories(val category : MainCategoryUi,val subCategories : SubCategoryUi):
        EditorEvent()
    internal data class ChangeNote(val note: String?): EditorEvent()
    internal data class ChangeParameters(val parameter : EditParameters): EditorEvent()
    internal data class AddSubCategory(val name : String): EditorEvent()
    internal data class NavigateToCategoryEditor(val category: MainCategoryUi): EditorEvent()
    internal data class NavigateToSubCategoryEditor(val category: SubCategoryUi): EditorEvent()
    internal data object PressDeleteButton : EditorEvent()
    internal data object PressSaveButton : EditorEvent()
    internal data object PressControlTemplateButton : EditorEvent()
    internal data object PressBackButton : EditorEvent()
}
@Parcelize
 data class EditorViewState(
    val editModel : EditModelUi? = null,
     val categories : List<CategoriesUi> = emptyList(),
    val templates : List<TemplateUi>? = null,
    val undefinedTasks : List<UndefinedTaskUi>? = null,
    val timeRangeValid : TimeRangeError? = null,
    val categoryValid : CategoryValidateError? = null,
): BaseViewState

