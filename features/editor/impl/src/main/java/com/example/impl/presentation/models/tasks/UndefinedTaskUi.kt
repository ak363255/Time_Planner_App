package com.example.impl.presentation.models.tasks

import android.os.Parcelable
import com.example.domain.entities.schedules.TaskPriority
import com.example.impl.presentation.models.categories.editor.MainCategoryUi
import com.example.impl.presentation.models.categories.editor.SubCategoryUi
import com.example.impl.presentation.models.editmodel.EditModelUi
import com.example.impl.presentation.models.editmodel.EditParameters
import com.example.utils.functional.TimeRange
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
internal data class UndefinedTaskUi(
    val id:Long = 0L,
    val createdAt : Date? = null,
    val deadline : Date? = null,
    val mainCategory : MainCategoryUi,
    val subCategoryUi: SubCategoryUi? = null,
    val priority: TaskPriority = TaskPriority.STANDARD,
    val note : String? = null,
): Parcelable

internal fun UndefinedTaskUi.convertToEditModel(
    scheduleDate : Date,
    timeRange : TimeRange
) = EditModelUi(
    date = scheduleDate,
    timeRange = timeRange,
    createdAt = createdAt,
    mainCategory = mainCategory,
    subCategory = subCategoryUi,
    parameters = EditParameters(priority = priority),
    undefinedTaskId = id,
    note = note
)