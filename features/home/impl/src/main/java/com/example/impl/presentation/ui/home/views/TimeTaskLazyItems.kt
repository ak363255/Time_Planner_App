package com.example.impl.presentation.ui.home.views

import android.graphics.Paint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.entities.schedules.TimeTaskStatus
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.impl.presentation.theme.HomeThemeRes
import com.example.presentation.ui.mappers.mapToIcon
import com.example.presentation.ui.mappers.mapToIconPainter
import com.example.presentation.ui.views.toMinutesOrHoursTitle
import com.example.utils.extensions.duration
import com.example.utils.extensions.toMinutesOrHoursString
import com.example.utils.functional.Constants
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun LazyItemScope.AddTimeTaskViewItem(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onAddClick: () -> Unit,
    startTime: Date,
    endTime: Date,
    indicatorColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Row(
        modifier = modifier.alpha(if (enabled) 1f else 0.6f),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StartTaskTimeTitle(time = startTime)
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .weight(1f)
                ) {
                    LinearProgressIndicator(
                        progress = { 0f },
                        modifier = Modifier
                            .height(4.dp)
                            .clip(MaterialTheme.shapes.small)
                            .fillMaxWidth(),
                        trackColor = indicatorColor,
                        strokeCap = StrokeCap.Square,
                        gapSize = 0.dp,
                        drawStopIndicator = {}
                    )
                }
            }
            if (enabled) {
                AddTimeTaskView(
                    showAddIconForFreeTime = startTime >= Date(),
                    isFreeTime = duration(startTime, endTime) >= Constants.Date.MILLIS_IN_MINUTE,
                    remainingTimeTitle = duration(startTime, endTime).toMinutesOrHoursTitle(),
                    onViewClicked = onAddClick
                )
            }


        }


    }

}

@Composable
fun StartTaskTimeTitle(
    modifier: Modifier = Modifier,
    time: Date,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val timeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)
    Text(
        modifier = modifier.defaultMinSize(minWidth = 42.dp),
        text = timeFormat.format(time),
        style = MaterialTheme.typography.titleMedium,
        color = color
    )

}

@Composable
internal fun LazyItemScope.TimeTaskViewItem(
    modifier: Modifier = Modifier,
    timeTask: TimeTaskUi,
    onEdit: (TimeTaskUi) -> Unit,
    onIncrease: (TimeTaskUi) -> Unit,
    onReduce: (TimeTaskUi) -> Unit,
    onDoneChange: (TimeTaskUi) -> Unit,
    isCompactView: Boolean,
) {
    when (timeTask.executionStatus) {
        TimeTaskStatus.PLANNED -> {
            PlannedTimeTaskItem(
                modifier = modifier,
                model = timeTask,
                onItemClick = { onEdit.invoke(timeTask) },
                isCompactView = isCompactView
            )
        }

        TimeTaskStatus.RUNNING -> {
            RunningTimeTaskItem(
                modifier = modifier,
                model = timeTask,
                onMoreButtonClick = {
                    onEdit.invoke(timeTask)
                },
                onIncreaseTime = { onIncrease.invoke(timeTask) },
                onReduceTime = { onReduce.invoke(timeTask) },
                isCompactView = isCompactView
            )
        }

        TimeTaskStatus.COMPLETED -> {
            CompletedTimeTaskItem(
                modifier = modifier,
                model = timeTask,
                onItemClick = {onEdit.invoke(timeTask)},
                onDoneChange = {onDoneChange.invoke(timeTask)},
                isCompactView = isCompactView
            )
        }
    }

}

@Composable
internal fun LazyItemScope.RunningTimeTaskItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    onMoreButtonClick: (Long) -> Unit,
    onIncreaseTime: () -> Unit,
    onReduceTime: () -> Unit,
    isCompactView: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StartTaskTimeTitle(time = model.startTime)
            Box(modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f)) {
                LinearProgressIndicator(
                    progress = { model.progress },
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .animateContentSize(),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Square,
                    gapSize = 0.dp,
                    drawStopIndicator = {}
                )
            }
            Text(
                text = model.leftTime.toMinutesOrHoursTitle(),
                color = MaterialTheme.colorScheme.primary
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            with(model) {
                EndTaskTimeTitle(
                    modifier = Modifier.align(Alignment.Bottom),
                    time = model.endTime,
                    isVisible = isCompactView
                )
                RunningTimeTask(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                    onMoreButtonClick = { onMoreButtonClick.invoke(key) },
                    onIncreaseTime = onIncreaseTime,
                    onReduceTime = onReduceTime,
                    taskTitle = mainCategory.fetchName() ?: HomeThemeRes.strings.noneTitle,
                    taskSubTitle = subCategory?.name,
                    categoryIcon = mainCategory.defaultType?.mapToIconPainter(),
                    priority = priority,
                    note = note,
                )
            }
        }

    }

}

@Composable
internal fun LazyItemScope.PlannedTimeTaskItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    isCompactView: Boolean = true,
    onItemClick: (Long) -> Unit
) {
    Column(
        modifier = modifier.padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StartTaskTimeTitle(time = model.startTime)
            LinearProgressIndicator(
                progress = { 0f },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Square,
                gapSize = 0.dp,
                drawStopIndicator = {}
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            with(model) {

            }
        }

    }
}

@Composable
fun EndTaskTimeTitle(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    time: Date
) {
    val timeFormat =
        android.icu.text.SimpleDateFormat.getTimeInstance(android.icu.text.SimpleDateFormat.SHORT)
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        label = "alpha",
        animationSpec = tween(durationMillis = 220)
    )
    Text(
        modifier = modifier
            .defaultMinSize(minWidth = 42.dp)
            .alpha(alpha),
        text = timeFormat.format(time),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.titleMedium,
    )

}

@Composable
internal fun LazyItemScope.CompletedTimeTaskItem(
    modifier: Modifier = Modifier,
    model: TimeTaskUi,
    isCompactView: Boolean = true,
    onItemClick: (Long) -> Unit,
    onDoneChange: () -> Unit
) {
    Column(
        modifier = modifier.padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StartTaskTimeTitle(time = model.startTime)
            LinearProgressIndicator(
                progress = { 1f },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                color = when (model.isCompleted) {
                    true -> MaterialTheme.colorScheme.tertiary
                    false -> MaterialTheme.colorScheme.error
                },
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Square,
                gapSize = 0.dp,
                drawStopIndicator = {},
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                with(model) {
                    EndTaskTimeTitle(
                        modifier = Modifier.align(Alignment.Bottom),
                        isVisible = isCompactView,
                        time = model.endTime
                    )
                    CompletedTimeTask(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp).animateContentSize(),
                        onViewClicked = {onItemClick.invoke(key)},
                        onDoneChange = onDoneChange,
                        taskTitle = mainCategory.fetchName()?: HomeThemeRes.strings.noneTitle,
                        taskSubTitle = subCategory?.name,
                        categoryIcon = mainCategory.defaultType?.mapToIconPainter(),
                        isCompleted = isCompleted,
                        note = note
                    )
                }
            }

        }
    }

}

@Composable
internal fun LazyItemScope.EmptyItem(height: Dp = 50.dp) {
    Spacer(modifier = Modifier.height(height).fillMaxWidth())
}
