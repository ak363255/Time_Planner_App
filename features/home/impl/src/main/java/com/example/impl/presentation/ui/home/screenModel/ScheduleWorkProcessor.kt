package com.example.impl.presentation.ui.home.screenModel

import com.example.domain.entities.schedules.TimeTask
import com.example.domain.entities.schedules.TimeTaskStatus
import com.example.domain.entities.settings.ViewToggleStatus
import com.example.impl.domain.interactors.ScheduleInteractor
import com.example.impl.domain.interactors.SettingsInteractor
import com.example.impl.domain.interactors.TimeShiftInteractor
import com.example.impl.presentation.common.TimeTaskStatusController
import com.example.impl.presentation.mappers.schedules.ScheduleDomainToUiMapper
import com.example.impl.presentation.mappers.schedules.mapToDomain
import com.example.impl.presentation.models.schedules.ScheduleUi
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.impl.presentation.ui.home.contract.HomeAction
import com.example.impl.presentation.ui.home.contract.HomeEffect
import com.example.presentation.ui.notifications.TimeTaskAlarmManager
import com.example.utils.functional.Constants
import com.example.utils.functional.Either
import com.example.utils.functional.collectAndHandle
import com.example.utils.functional.handle
import com.example.utils.functional.rightOrElse
import com.example.utils.functional.rightOrError
import com.example.utils.managers.DateManager
import com.example.utils.platform.screenmodel.work.ActionResult
import com.example.utils.platform.screenmodel.work.EffectResult
import com.example.utils.platform.screenmodel.work.FlowWorkProcessor
import com.example.utils.platform.screenmodel.work.WorkCommand
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date

 interface ScheduleWorkProcessor : FlowWorkProcessor<ScheduleWorkCommand, HomeAction, HomeEffect> {
    class Base(
        private val scheduleInteractor: ScheduleInteractor,
        private val timeShiftInteractor: TimeShiftInteractor,
        private val settingsInteractor: SettingsInteractor,
        private val mapperToUi: ScheduleDomainToUiMapper,
        private val statusController: TimeTaskStatusController,
        private val dateManager: DateManager,
        private val timeTaskAlarmManager: TimeTaskAlarmManager

    ) : ScheduleWorkProcessor {
        override suspend fun work(command: ScheduleWorkCommand) =
            when (command) {
                is ScheduleWorkCommand.SetupSettings -> setupSettings()
                is ScheduleWorkCommand.LoadScheduleByDate -> loadScheduleByDateWork(command.date)
                is ScheduleWorkCommand.ChangeTaskDoneState -> changeTaskDoneStateWork(
                    command.date,
                    command.key
                )

                is ScheduleWorkCommand.ChangeTaskViewStatus -> changeTaskViewStatus(command.status)
                is ScheduleWorkCommand.CreateSchedule -> createScheduleWork(command.date)
                is ScheduleWorkCommand.TimeTaskShiftDown -> shiftDownTimeWork(command.timeTask)
                is ScheduleWorkCommand.TimeTaskShiftUp -> shiftUpTimeWork(command.timeTask)
            }

        private fun setupSettings() = flow {
            settingsInteractor.fetchTaskSetting().collectAndHandle(
                onLeftAction = { emit(EffectResult(HomeEffect.ShowError(it))) },
                onRightAction = { emit(ActionResult(HomeAction.SetupSettings(it))) },
            )
        }

        private suspend fun loadScheduleByDateWork(date: Date?) = channelFlow {
            var cycleUpdateJob: Job? = null
            val sendDate = scheduleInteractor.fetchFeatureScheduleDate()
            val scheduleDate = sendDate ?: date ?: dateManager.fetchBeginningCurrentDay()
            scheduleInteractor.fetchScheduleByDate(scheduleDate.time).collect { scheduleEither ->
                cycleUpdateJob?.cancelAndJoin()
                scheduleEither.handle(
                    onLeftAction = { error -> send(EffectResult(HomeEffect.ShowError(error))) },
                    onRightAction = { scheduleModel ->
                        if (scheduleModel != null) {
                            val schedule = scheduleModel.map(mapperToUi)

                            send(ActionResult(HomeAction.UpdateSchedule(schedule)))

                            cycleUpdateJob = refreshScheduleState(schedule)
                                .onEach { send(it) }
                                .launchIn(this)
                                .apply { start() }
                        } else {
                            send(ActionResult(HomeAction.SetEmptySchedule(scheduleDate, null)))
                        }
                    },
                )
            }

        }

        private suspend fun refreshScheduleState(schedule: ScheduleUi) = flow {
            var oldTimeTasks = schedule.timeTasks
            var isWorking = true
            while (isWorking) {
                val newTimeTasks = oldTimeTasks.map { statusController.updateStatus(it) }
                if (newTimeTasks != oldTimeTasks || schedule.timeTasks == oldTimeTasks) {
                    val completedChange =
                        oldTimeTasks.map { it.isCompleted } != newTimeTasks.map { it.isCompleted }
                    oldTimeTasks = newTimeTasks
                    val newSchedule = schedule.copy(timeTasks = oldTimeTasks)
                    emit(ActionResult(HomeAction.UpdateSchedule(newSchedule)))
                    if (completedChange) scheduleInteractor.updateSchedule(newSchedule.mapToDomain())
                }
                isWorking =
                    oldTimeTasks.find { it.executionStatus != TimeTaskStatus.COMPLETED } != null
                delay(Constants.Delay.CHECK_STATUS)
            }
        }

        private fun changeTaskDoneStateWork(date: Date, key: Long) = flow {
            val schedule =
                scheduleInteractor.fetchScheduleByDate(date.time).firstOrNull()?.rightOrElse(null)
            if (schedule != null) {
                val timeTasks = schedule.timeTasks.toMutableList().apply {
                    val oldTimeTaskIndex = indexOfFirst { it.key == key }
                    val oldTimeTask = get(oldTimeTaskIndex)
                    val newTimeTask = oldTimeTask.copy(isCompleted = !oldTimeTask.isCompleted)
                    set(oldTimeTaskIndex, newTimeTask)
                }
                val newSchedule = schedule.copy(timeTasks = timeTasks)
                scheduleInteractor.updateSchedule(newSchedule).handle(
                    onLeftAction = { emit(EffectResult(HomeEffect.ShowError(it))) },
                )
            }
        }

        private fun changeTaskViewStatus(status: ViewToggleStatus) = flow {
            val oldSettings = settingsInteractor.fetchTaskSetting().first()
                .rightOrError("Error get tasks settings!")
            val newSettings = oldSettings.copy(taskViewStatus = status)
            settingsInteractor.updateTasksSetting(newSettings).handle(
                onLeftAction = { emit(EffectResult(HomeEffect.ShowError(it))) },
            )
        }

        private suspend fun createScheduleWork(date: Date): Flow<Either<HomeAction, HomeEffect>> = flow {
            scheduleInteractor.createSchedule(date).handle(
                //onLeftAction = { emit(EffectResult(HomeEffect.ShowError(it))) },
            )
        }

        private suspend fun shiftUpTimeWork(timeTask: TimeTaskUi) = flow {
            val shiftValue = Constants.Date.SHIFT_MINUTE_VALUE
            timeShiftInteractor.shiftUpTimeTask(timeTask.mapToDomain(), shiftValue).handle(
                onLeftAction = { emit(EffectResult(HomeEffect.ShowError(it))) },
                onRightAction = { updatedTasks -> updatedTasks.forEach { notifyUpdate(it) } },
            )
        }

        private suspend fun shiftDownTimeWork(timeTask: TimeTaskUi) = flow {
            val shiftValue = Constants.Date.SHIFT_MINUTE_VALUE
            timeShiftInteractor.shiftDownTimeTask(timeTask.mapToDomain(), shiftValue).handle(
                onLeftAction = { emit(EffectResult(HomeEffect.ShowError(it))) },
                onRightAction = { notifyUpdate(it) },
            )
        }

        private fun notifyUpdate(timeTask: TimeTask) {
            if (timeTask.isEnableNotification) {
                timeTaskAlarmManager.deleteNotifyAlarm(timeTask)
                timeTaskAlarmManager.addOrUpdateNotifyAlarm(timeTask)
            }
        }


    }

}

sealed class ScheduleWorkCommand : WorkCommand {
    internal data object SetupSettings : ScheduleWorkCommand()
    internal data class LoadScheduleByDate(val date: Date?) : ScheduleWorkCommand()
    internal data class CreateSchedule(val date: Date) : ScheduleWorkCommand()
    internal data class ChangeTaskDoneState(val date: Date, val key: Long) : ScheduleWorkCommand()
    internal data class ChangeTaskViewStatus(val status: ViewToggleStatus) : ScheduleWorkCommand()
    internal data class TimeTaskShiftUp(val timeTask: TimeTaskUi) : ScheduleWorkCommand()
    internal data class TimeTaskShiftDown(val timeTask: TimeTaskUi) : ScheduleWorkCommand()
}