package com.example.impl.presentation.models.editmodel

import android.os.Parcelable
import com.example.domain.entities.Template.RepeatTime
import com.example.impl.presentation.models.categories.MainCategoryUi
import com.example.impl.presentation.models.categories.SubCategoryUi
import com.example.utils.extensions.duration
import com.example.utils.functional.TimeRange
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
data class EditModelUi(
    val key : Long = 0L,
    val date : Date,
    val timeRange: TimeRange,
    val createdAt : Date? = null,
    val duration : Long = duration(timeRange.from,timeRange.to),
    val mainCategory : MainCategoryUi = MainCategoryUi(),
    val subCategory : SubCategoryUi? = null,
    val isCompleted : Boolean = true,
    val parameters : EditParameters,
    val repeatEnabled : Boolean = false,
    val templateId : Int? = null,
    val undefinedTaskId : Long? = null,
    val repeatTimes : List<RepeatTime> = emptyList(),
    val note : String? = null
    ): Parcelable{
    fun checkDateIsRepeat() : Boolean{
        return repeatEnabled && repeatTimes.find { it.checkDateIsRepeat(date) } != null
    }
    }