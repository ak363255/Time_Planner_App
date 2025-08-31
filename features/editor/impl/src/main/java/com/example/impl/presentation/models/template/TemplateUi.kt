package com.example.impl.presentation.models.template

import android.os.Parcelable
import com.example.domain.entities.Template.RepeatTime
import com.example.domain.entities.schedules.TaskPriority
import com.example.impl.presentation.models.categories.editor.MainCategoryUi
import com.example.impl.presentation.models.categories.editor.SubCategoryUi
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
internal data class TemplateUi(
    val templateId :Int,
    val startTime : Date,
    val endTime : Date,
    val category : MainCategoryUi,
    val subCategory : SubCategoryUi? = null,
    val priority: TaskPriority = TaskPriority.STANDARD,
    val isEnableNotification : Boolean = true,
    val isConsiderInStatistics : Boolean = true,
    val repeatEnabled : Boolean = true,
    val repeatTimes : List<RepeatTime> = emptyList()
): Parcelable