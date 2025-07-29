package com.example.impl.presentation.ui.home.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.domain.entities.schedules.DailyScheduleStatus
import com.example.domain.entities.schedules.TimeTaskStatus
import com.example.domain.entities.settings.ViewToggleStatus
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.impl.presentation.theme.HomeThemeRes
import com.example.impl.presentation.ui.home.contract.HomeViewState
import com.example.presentation.ui.theme.TimePlannerRes
import com.example.presentation.ui.views.ViewToggle
import com.example.utils.extensions.endThisDay
import com.example.utils.extensions.isCurrentDay
import com.example.utils.extensions.isNotZeroDifference
import com.example.utils.extensions.shiftDay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeContent(
    state : HomeViewState,
    modifier : Modifier = Modifier,
    onChangeDate : (Date)->Unit,
    onCreateSchedule : () ->Unit,
    onTimeTaskEdit : (TimeTaskUi) ->Unit,
    onTaskDoneChange : (TimeTaskUi) ->Unit,
    onTimeTaskAdd : (startTime: Date,endTime: Date)->Unit,
    onTimeTaskIncrease : (TimeTaskUi) ->Unit,
    onTimeTaskReduce : (TimeTaskUi)-> Unit,
    onChangeToggleStatus : (ViewToggleStatus) -> Unit
){
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DateChooserSection(
            visible = state.currentDate != null,
            currentDate = state.currentDate,
            toggleState = state.taskViewStatus,
            onChangeDate = onChangeDate,
            onChangeToggleStatus = onChangeToggleStatus
        )
        TimeTasksSection(
            currentDate = state.currentDate,
            dateStatus = state.dateStatus,
            timeTasks = state.timeTasks,
            timeTaskViewStatus = state.taskViewStatus,
            onCreateSchedule = onCreateSchedule,
            onTimeTaskEdit = onTimeTaskEdit,
            onTaskDoneChange = onTaskDoneChange,
            onTimeTaskAdd = onTimeTaskAdd,
            onTimeTaskIncrease = onTimeTaskIncrease,
            onTimeTaskReduce = onTimeTaskReduce,
        )

    }
}

@Composable
fun DateChooserSection(
    modifier : Modifier = Modifier,
    visible : Boolean = true,
    currentDate : Date?,
    toggleState : ViewToggleStatus,
    onChangeDate : (Date)->Unit,
    onChangeToggleStatus : (ViewToggleStatus)-> Unit
){
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically()
    ) {
        Row(
            modifier = modifier.padding(start = 16.dp, end = 8.dp,top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HomeDateChooser(
                modifier = Modifier.width(202.dp),
                currentDate = currentDate,
                onChangeDate = onChangeDate
            )
            Spacer(modifier = Modifier.weight(1f))
            ViewToggle (
                status = toggleState,
                onStatusChange = onChangeToggleStatus
            )

        }
    }

}

@Composable
fun HomeDateChooser(
    modifier : Modifier = Modifier,
    enabled : Boolean = true,
    currentDate : Date?,
    onChangeDate : (Date) -> Unit
){
    val dateFormat = SimpleDateFormat("EEE, d MMM", Locale.getDefault())
    val isDateDialogShow = rememberSaveable {
        mutableStateOf(false)
    }
    DateChooser(
        modifier = modifier,
        enabled = enabled,
        dateTitle = currentDate?.let { dateFormat.format(it) }?:"",
        onNext = {currentDate?.let { onChangeDate.invoke(it.shiftDay(amount = 1)) }},
        onPrevious = {currentDate?.let { onChangeDate.invoke(it.shiftDay(amount =-1)) }},
        onChooseDate = {isDateDialogShow.value = true}
    )
    HomeDatePicker(
        isOpenDialog = isDateDialogShow.value,
        onDismiss = {isDateDialogShow.value=false},
        onSelectedDate = {
            isDateDialogShow.value = false
            onChangeDate.invoke(it)
        }
    )


}

@Composable
fun TimeTasksSection(
    modifier : Modifier = Modifier,
    listState : LazyListState = rememberLazyListState(),
    dateStatus : DailyScheduleStatus?,
    currentDate : Date?,
    timeTasks : List<TimeTaskUi>,
    timeTaskViewStatus : ViewToggleStatus,
    onCreateSchedule : ()->Unit,
    onTimeTaskEdit : (TimeTaskUi)->Unit,
    onTaskDoneChange : (TimeTaskUi)->Unit,
    onTimeTaskAdd : (startTime : Date,endTime: Date)-> Unit,
    onTimeTaskIncrease : (TimeTaskUi)->Unit,
    onTimeTaskReduce : (TimeTaskUi)-> Unit

)=AnimatedVisibility(
    visible = currentDate != null,
    enter = fadeIn() + scaleIn(initialScale = 0.9f),
    exit = fadeOut()
){
    val isCompactView = timeTaskViewStatus == ViewToggleStatus.COMPACT
    var isScrolled by rememberSaveable { mutableStateOf(false) }
    val visibleFirstAdd = timeTasks.isNotEmpty() && timeTasks.first().startTime > currentDate && !isCompactView

    Box(
        modifier = modifier.fillMaxSize()
    ){
        if(dateStatus != null){
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if(visibleFirstAdd){
                    item {
                        val startTime = checkNotNull(currentDate)
                        val endTime = timeTasks[0].startTime
                        AddTimeTaskViewItem(
                            modifier = Modifier,
                            onAddClick = {
                                onTimeTaskAdd(startTime,endTime)
                            },
                            startTime = startTime,
                            endTime = endTime
                        )
                    }
                }
                items(
                    count = timeTasks.size, key = {index ->
                        timeTasks[index].key
                    }
                ){index ->
                    val timeTaskIndex = index
                    val nextItem = timeTasks.getOrNull(timeTaskIndex +1)
                    TimeTaskViewItem(
                        modifier = Modifier,
                        timeTask = timeTasks[index],
                        onEdit = onTimeTaskEdit,
                        onIncrease = onTimeTaskIncrease,
                        onReduce = onTimeTaskReduce,
                        onDoneChange = onTaskDoneChange,
                        isCompactView = isCompactView && nextItem != null && timeTasks[index].endTime.isNotZeroDifference(
                            nextItem.startTime,
                        ),
                    )
                    AnimatedVisibility(
                        enter = fadeIn() + slideInVertically(),
                        exit = shrinkVertically() + fadeOut(),
                        visible = nextItem != null &&
                                timeTasks[index].endTime.isNotZeroDifference(nextItem.startTime) &&
                                !isCompactView,
                    ) {
                        val trackColor = when (timeTasks[index].executionStatus) {
                            TimeTaskStatus.PLANNED -> MaterialTheme.colorScheme.surfaceContainerLow
                            TimeTaskStatus.RUNNING -> MaterialTheme.colorScheme.primaryContainer
                            TimeTaskStatus.COMPLETED -> MaterialTheme.colorScheme.tertiaryContainer
                        }
                        if (nextItem != null) {
                            AddTimeTaskViewItem(
                                modifier = Modifier,
                                onAddClick = { onTimeTaskAdd.invoke(timeTasks[index].endTime, nextItem.startTime) },
                                startTime = timeTasks[index].endTime,
                                endTime = nextItem.startTime,
                                indicatorColor = trackColor,
                            )
                        }
                    }

                }
                item {
                    val startTime = when (timeTasks.isEmpty()) {
                        true -> checkNotNull(currentDate)
                        false -> timeTasks.last().endTime
                    }
                    val endTime = startTime.endThisDay()
                    AddTimeTaskViewItem(
                        modifier = Modifier,
                        enabled = timeTasks.isEmpty() || timeTasks.last().endTime.isCurrentDay(currentDate!!),
                        onAddClick = { onTimeTaskAdd(startTime, endTime) },
                        startTime = startTime,
                        endTime = endTime,
                    )
                }
                item { EmptyItem() }

            }
        }
        else if (currentDate != null) {
            EmptyDateView(
                modifier = Modifier.align(Alignment.Center),
                emptyTitle = TimePlannerRes.strings.emptyScheduleTitle,
                subTitle = null,
            ) {
                OutlinedButton(
                    onClick = onCreateSchedule,
                    modifier = Modifier.width(185.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary,
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(18.dp)
                            .align(Alignment.CenterVertically),
                        imageVector = Icons.Default.Add,
                        contentDescription = HomeThemeRes.strings.createScheduleDesc,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .align(Alignment.CenterVertically),
                        text = HomeThemeRes.strings.createScheduleTitle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

    }

}
