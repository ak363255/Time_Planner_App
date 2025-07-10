package com.example.impl.presentation.ui.home.views

import com.example.domain.entities.settings.ViewToggleStatus
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.impl.presentation.ui.home.contract.HomeAction
import com.example.impl.presentation.ui.home.contract.HomeEffect
import com.example.impl.presentation.ui.home.contract.HomeEvent
import com.example.utils.platform.screenmodel.work.FlowWorkProcessor
import com.example.utils.platform.screenmodel.work.FlowWorkResult
import com.example.utils.platform.screenmodel.work.WorkCommand
import java.util.Date

interface ScheduleWorkProcessor : FlowWorkProcessor<ScheduleWorkCommand, HomeAction, HomeEffect>{
    class Base(

    ): ScheduleWorkProcessor{
        override suspend fun work(command: ScheduleWorkCommand): FlowWorkResult<HomeAction, HomeEffect> {

        }

    }
}

sealed class ScheduleWorkCommand : WorkCommand{
    data object SetupSettings : ScheduleWorkCommand()
    data class LoadScheduleByDate(val date : Date?): ScheduleWorkCommand()
    data class CreateSchedule(val date : Date) : ScheduleWorkCommand()
    data class ChangeTaskDoneState(val date : Date,val key : Long): ScheduleWorkCommand()
    data class ChangeTaskViewStatus(val status : ViewToggleStatus): ScheduleWorkCommand()
    data class TimeTaskShiftUp(val timeTask : TimeTaskUi) : ScheduleWorkCommand()
    data class TimeTaskShiftDown(val timeTask : TimeTaskUi) : ScheduleWorkCommand()
}