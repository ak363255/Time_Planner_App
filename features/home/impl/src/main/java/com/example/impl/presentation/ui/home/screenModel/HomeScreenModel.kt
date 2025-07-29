package com.example.impl.presentation.ui.home.screenModel

import com.example.domain.entities.settings.ViewToggleStatus
import com.example.impl.presentation.ui.home.contract.HomeAction
import com.example.impl.presentation.ui.home.contract.HomeEffect
import com.example.impl.presentation.ui.home.contract.HomeEvent
import com.example.impl.presentation.ui.home.contract.HomeViewState
import com.example.utils.functional.TimeRange
import com.example.utils.managers.CoroutineManager
import com.example.utils.platform.screenmodel.BaseViewModel
import com.example.utils.platform.screenmodel.EmptyDeps
import com.example.utils.platform.screenmodel.work.BackgroundWorkKey
import com.example.utils.platform.screenmodel.work.WorkScope

class HomeScreenModel(
 private val scheduleWorkProcessor: ScheduleWorkProcessor,
 private val navigationWorkProcessor: NavigationWorkProcessor,
    stateCommunicator : HomeStateCommunicator,
    effectCommunicator : HomeEffectCommunicator,
    coroutineManager: CoroutineManager
): BaseViewModel<HomeViewState, HomeEvent, HomeAction, HomeEffect, EmptyDeps>(
    stateCommunicator = stateCommunicator,
    effectCommunicator = effectCommunicator,
    coroutineManager = coroutineManager
) {

    override fun init(deps: EmptyDeps) {
        if(!isInitialize.get()){
            super.init(deps)
            dispatchEvent(HomeEvent.Init)
        }
    }
    override suspend fun WorkScope<HomeViewState, HomeAction, HomeEffect>.handleEvent(
        event: HomeEvent
    ) {
        when (event) {
            is HomeEvent.Init -> {
                launchBackgroundWork(BackgroundKey.SETUP_SETTINGS) {
                    val setupCommand = ScheduleWorkCommand.SetupSettings
                    scheduleWorkProcessor.work(setupCommand).collectAndHandleWork()
                }
            }
            is HomeEvent.LoadSchedule -> launchBackgroundWork(BackgroundKey.LOAD_SCHEDULE) {
                val command = ScheduleWorkCommand.LoadScheduleByDate(event.date)
                scheduleWorkProcessor.work(command).collectAndHandleWork()
            }
            is HomeEvent.CreateSchedule -> launchBackgroundWork(BackgroundKey.CREATE_SCHEDULE){
                val currentDate = checkNotNull(state().currentDate)
                val createCommand = ScheduleWorkCommand.CreateSchedule(currentDate)
                scheduleWorkProcessor.work(createCommand).collectAndHandleWork()
            }
            is HomeEvent.TimeTaskShiftUp -> launchBackgroundWork(BackgroundKey.DATA_ACTION) {
                val shiftUpCommand = ScheduleWorkCommand.TimeTaskShiftUp(event.timeTask)
                scheduleWorkProcessor.work(shiftUpCommand).collectAndHandleWork()
            }
            is HomeEvent.TimeTaskShiftDown -> launchBackgroundWork(BackgroundKey.DATA_ACTION) {
                val shiftDownCommand = ScheduleWorkCommand.TimeTaskShiftDown(event.timeTask)
                scheduleWorkProcessor.work(shiftDownCommand).collectAndHandleWork()
            }
            is HomeEvent.ChangeTaskDoneStateButton -> launchBackgroundWork(BackgroundKey.DATA_ACTION) {
                val date = checkNotNull(state().currentDate)
                val changeStatusCommand = ScheduleWorkCommand.ChangeTaskDoneState(date, event.timeTask.key)
                scheduleWorkProcessor.work(changeStatusCommand).collectAndHandleWork()
            }
            is HomeEvent.PressViewToggleButton -> launchBackgroundWork(BackgroundKey.DATA_ACTION) {
                val status = when (event.status) {
                    ViewToggleStatus.EXPANDED -> ViewToggleStatus.COMPACT
                    ViewToggleStatus.COMPACT -> ViewToggleStatus.EXPANDED
                }
                val changeCommand = ScheduleWorkCommand.ChangeTaskViewStatus(status)
                scheduleWorkProcessor.work(changeCommand).collectAndHandleWork()
            }
            is HomeEvent.PressEditTimeTaskButton -> {
              //  val navCommand = NavigationWorkCommand.NavigateToEditor(timeTask = event.timeTask)
             //   navigationWorkProcessor.work(navCommand).handleWork()
            }
            is HomeEvent.PressAddTimeTaskButton -> {
                val navCommand = NavigationWorkCommand.NavigateToEditorCreator(
                    currentDate = checkNotNull(state().currentDate),
                    timeRange = TimeRange(event.startTime, event.endTime),
                )
                navigationWorkProcessor.work(navCommand).handleWork()
            }
            is HomeEvent.PressOverviewButton -> {
               /* val navCommand = NavigationWorkCommand.NavigateToOverview
                navigationWorkProcessor.work(navCommand).handleWork()*/
            }
        }

    }
    override suspend fun reduce(
        action: HomeAction,
        currentState: HomeViewState
    ): HomeViewState =  when (action) {
        is HomeAction.SetupSettings -> currentState.copy(
        taskViewStatus = action.settings.taskViewStatus,
        calendarButtonBehavior = action.settings.calendarButtonBehavior,
        )
        is HomeAction.SetEmptySchedule -> currentState.copy(
        timeTasks = emptyList(),
        currentDate = action.date,
        dateStatus = action.status,
        )
        is HomeAction.UpdateSchedule -> currentState.copy(
        timeTasks = action.schedule.timeTasks,
        currentDate = action.schedule.date,
        dateStatus = action.schedule.dateStatus,
        )
    }

    enum class BackgroundKey : BackgroundWorkKey {
        LOAD_SCHEDULE, SETUP_SETTINGS, CREATE_SCHEDULE, DATA_ACTION
    }

}