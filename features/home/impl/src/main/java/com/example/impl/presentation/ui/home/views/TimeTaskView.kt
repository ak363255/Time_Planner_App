package com.example.impl.presentation.ui.home.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.domain.entities.schedules.TaskPriority
import com.example.impl.presentation.theme.HomeThemeRes
import com.example.presentation.ui.mappers.mapToUi
import com.example.presentation.ui.views.CategoryIconMonogram
import com.example.presentation.ui.views.CategoryTextMonogram
import com.example.presentation.ui.views.ExpandedIcon
import kotlin.collections.remove

@Composable
fun AddTimeTaskView(
    modifier : Modifier = Modifier,
    showAddIconForFreeTime : Boolean = true,
    isFreeTime : Boolean,
    remainingTimeTitle : String,
    onViewClicked : ()-> Unit
){
    Surface(
        onClick  = onViewClicked,
        modifier = modifier.height(46.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(showAddIconForFreeTime){
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = HomeThemeRes.strings.timeTaskIncreaseTimeTitle,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = if(isFreeTime) HomeThemeRes.strings.addFreeTimeTaskTitle else HomeThemeRes.strings.addTaskTitle,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            if(isFreeTime){
                Text(
                    text = remainingTimeTitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    }

}

@Composable
fun CompletedTimeTask(
    modifier : Modifier = Modifier,
    onViewClicked : () -> Unit,
    onDoneChange : () -> Unit,
    taskTitle : String,
    taskSubTitle : String?,
    categoryIcon:Painter?,
    isCompleted : Boolean,
    note: String?
){
    Surface(
        modifier = modifier,
        onClick =  onViewClicked,
        enabled = true,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = MaterialTheme.shapes.large
    ){
        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(modifier = Modifier.align(Alignment.Top)) {
                if (categoryIcon != null) {
                    CategoryIconMonogram(
                        icon = categoryIcon,
                        iconDescription = taskTitle,
                        iconColor = MaterialTheme.colorScheme.onTertiary,
                        backgroundColor = MaterialTheme.colorScheme.tertiary,
                    )
                } else {
                    CategoryTextMonogram(
                        text = taskTitle.first().toString(),
                        textColor = MaterialTheme.colorScheme.onTertiary,
                        backgroundColor = MaterialTheme.colorScheme.tertiary,
                    )
                }
            }
            TimeTaskTitles(
                modifier = Modifier.weight(1f),
                title = taskTitle,
                titleColor = MaterialTheme.colorScheme.onSurface,
                subTitle = taskSubTitle,
            )
            IconButton(
                modifier = Modifier.size(36.dp),
                onClick = onDoneChange,
            ) {
                if (isCompleted) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(HomeThemeRes.icons.check),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(HomeThemeRes.icons.cancel),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }

}
@Composable
internal fun TimeTaskTitles(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color,
    subTitle: String?,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            color = titleColor,
            style = MaterialTheme.typography.titleMedium,
        )
        if (subTitle != null) {
            Text(
                text = subTitle,
                modifier = Modifier.padding(top = 2.dp),
                color = titleColor,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
 fun RunningTimeTask(
    modifier: Modifier = Modifier,
    onMoreButtonClick: () -> Unit,
    onIncreaseTime: () -> Unit,
    onReduceTime: () -> Unit,
    taskTitle: String,
    taskSubTitle: String?,
    categoryIcon: Painter?,
    priority: TaskPriority,
    note: String?,
) {
    var expandedTask by rememberSaveable { mutableStateOf(false) }
    var expandedNote by rememberSaveable { mutableStateOf(false) }

    Surface(
        onClick = { expandedTask = !expandedTask },
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,

        ) {
        Column(modifier = Modifier.animateContentSize()) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp, top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(modifier = Modifier.align(Alignment.Top)) {
                    if (categoryIcon != null) {
                        CategoryIconMonogram(
                            icon = categoryIcon,
                            iconDescription = taskTitle,
                            iconColor = MaterialTheme.colorScheme.onPrimary,
                            priority = priority.mapToUi(),
                            backgroundColor = MaterialTheme.colorScheme.primary,
                        )
                    } else {
                        CategoryTextMonogram(
                            text = taskTitle.first().toString(),
                            textColor = MaterialTheme.colorScheme.onPrimary,
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            priority = priority.mapToUi(),
                        )
                    }
                }
                TimeTaskTitles(
                    modifier = Modifier.weight(1f),
                    title = taskTitle,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    subTitle = taskSubTitle,
                )
                Box(
                    modifier = Modifier.size(36.dp).animateContentSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    ExpandedIcon(
                        isExpanded = expandedTask,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        description = null,
                    )
                }
            }
            if (!note.isNullOrEmpty()) {
                TimeTaskNoteView(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    onClick = { expandedNote = !expandedNote },
                    text = note,
                    expanded = expandedNote,
                )
            } else {
                Spacer(modifier = Modifier.padding(bottom = 16.dp))
            }
            if (expandedTask) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = onIncreaseTime) {
                        Icon(
                            painter = painterResource(HomeThemeRes.icons.add),
                            contentDescription = HomeThemeRes.strings.timeTaskAddIconDesc,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = HomeThemeRes.strings.timeTaskIncreaseTimeTitle,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                    TextButton(onClick = onReduceTime) {
                        Icon(
                            painter = painterResource(HomeThemeRes.icons.remove),
                            contentDescription = HomeThemeRes.strings.timeTaskRemoveIconDesc,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = HomeThemeRes.strings.timeTaskReduceTimeTitle,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(modifier = Modifier.size(36.dp), onClick = onMoreButtonClick) {
                        Icon(
                            painter = painterResource(HomeThemeRes.icons.more),
                            contentDescription = HomeThemeRes.strings.timeTaskMoreIconDesc,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimeTaskNoteView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    expanded: Boolean,
    container: Color = MaterialTheme.colorScheme.primary,
    content: Color = MaterialTheme.colorScheme.onPrimary,
) {
    var isOverflow by remember { mutableStateOf(false) }

    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && isOverflow || enabled && expanded,
        shape = MaterialTheme.shapes.medium,
        color = container,
    ) {
        AnimatedContent(
            targetState = expanded,
            label = "Note",
        ) { isExpanded ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = HomeThemeRes.icons.notes),
                    contentDescription = HomeThemeRes.strings.noteTitle,
                    tint = content,
                )
                Text(
                    text = text,
                    color = content,
                    maxLines = when (isExpanded) {
                        true -> 4
                        false -> 1
                    },
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { result -> isOverflow = result.didOverflowHeight },
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}