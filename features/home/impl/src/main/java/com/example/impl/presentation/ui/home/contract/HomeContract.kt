package com.example.impl.presentation.ui.home.contract

import android.os.Parcelable
import com.example.domain.entities.schedules.DailyScheduleStatus
import com.example.domain.entities.schedules.TimeTask
import com.example.domain.entities.settings.CalendarButtonBehavior
import com.example.domain.entities.settings.TaskSettings
import com.example.domain.entities.settings.ViewToggleStatus
import com.example.impl.domain.entities.HomeFailures
import com.example.impl.presentation.models.schedules.ScheduleUi
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseRoute
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.util.Date
import kotlin.reflect.KClass

@Parcelize
data class HomeViewState(
    val currentDate: Date? = null,
    val dateStatus : DailyScheduleStatus? = null,
    val taskViewStatus : ViewToggleStatus = ViewToggleStatus.COMPACT,
    val calendarButtonBehavior: CalendarButtonBehavior = CalendarButtonBehavior.SET_CURRENT_DATE,
    val timeTasks : List<TimeTaskUi> = emptyList()
): BaseViewState

sealed class HomeEvent : BaseEvent{
    data object Init : HomeEvent()
    data object CreateSchedule : HomeEvent()
    data object PressOverviewButton : HomeEvent()
    data class LoadSchedule(val date : Date?) : HomeEvent()
    data class PressAddTimeTaskButton(val startTime : Date,val endTime : Date): HomeEvent()
    data class PressEditTimeTaskButton(val timeTask : TimeTaskUi) : HomeEvent()
    data class ChangeTaskDoneStateButton(val timeTask : TimeTaskUi) : HomeEvent()
    data class TimeTaskShiftUp(val timeTask : TimeTaskUi): HomeEvent()
    data class TimeTaskShiftDown(val timeTask : TimeTaskUi) : HomeEvent()
    data class PressViewToggleButton(val status : ViewToggleStatus) : HomeEvent()
}
sealed class HomeEffect : BaseUiEffect{
    data class ShowError(val failures : HomeFailures): HomeEffect()
    data class NavigateToEditorCreator(val timeTask : TimeTask) : HomeEffect()
}
sealed class HomeAction : BaseAction{
    data class SetupSettings(val settings: TaskSettings) : HomeAction()
    data class UpdateSchedule(val schedule : ScheduleUi) : HomeAction()
    data class SetEmptySchedule (val date : Date,val status : DailyScheduleStatus?) : HomeAction()
}
@Serializable
@Parcelize
sealed class HomePageRoute : BaseRoute{
    @Parcelize
    @Serializable
    data object HomePageMainScreen:HomePageRoute()

}
@Serializable
@Parcelize
data class User(val name : String): Parcelable{
    fun serialize(): String = Json.encodeToString(serializer(),this)
}