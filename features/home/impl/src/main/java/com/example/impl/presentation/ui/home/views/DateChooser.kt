package com.example.impl.presentation.ui.home.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.impl.presentation.theme.HomeThemeRes
import java.util.Date

@Composable
fun DateChooser(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    dateTitle: String,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onChooseDate: () -> Unit,
) {
    Surface(
        modifier = modifier.height(36.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
           DateChooserIcon(
               enabled = enabled,
               icon = painterResource(HomeThemeRes.icons.previousDate),
               description = HomeThemeRes.strings.previousDateIconDesc,
               onClick = onPrevious
           )
            DateChooserContent(
                modifier = Modifier.weight(1f),
                enabled = enabled,
                dateTitle = dateTitle,
                onClick = onChooseDate
            )

            DateChooserIcon(
                enabled = enabled,
                icon = painterResource(HomeThemeRes.icons.nextDate),
                description = HomeThemeRes.strings.nextDateIconDesc,
                onClick = onNext
            )

        }
    }
}

@Composable
fun DateChooserIcon(
    modifier: Modifier = Modifier,
    enabled : Boolean,
    icon : Painter,
    description : String?,
    onClick : () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(36.dp),
        enabled = enabled
    ) {
        Icon(
            modifier = Modifier.size(12.dp).graphicsLayer(alpha = if(enabled)1f else 0.5f),
            painter = icon,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

}

@Composable
fun DateChooserContent(
    modifier : Modifier = Modifier,
    enabled : Boolean,
    dateTitle: String,
    onClick: () -> Unit
){
    Box(
        modifier = modifier.fillMaxHeight().clip(MaterialTheme.shapes.medium).clickable(onClick = onClick, enabled = enabled),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.padding(horizontal = 12.dp).graphicsLayer(alpha = if(enabled) 1f else 0.5f),
            text = dateTitle,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDatePicker(
    modifier : Modifier = Modifier,
    isOpenDialog : Boolean,
    onDismiss : ()-> Unit,
    onSelectedDate : (Date) -> Unit

){
    if(isOpenDialog){
        val datePickerDate = rememberDatePickerState()
        val confirmEnabled by remember {
            derivedStateOf { datePickerDate.selectedDateMillis != null }
        }
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            confirmButton = {},
            dismissButton = {}
        ) {
            DatePicker(
                state = datePickerDate,
                title = {
                    Text(
                        modifier = Modifier.padding(start = 24.dp,top = 24.dp),
                        text = HomeThemeRes.strings.dateDialogPickerTitle
                    )
                },
                headline = {
                    Text(
                        modifier = Modifier.padding(start = 24.dp),
                        text = HomeThemeRes.strings.dateDialogPickerHeadline
                    )
                }
            )
        }

    }

}