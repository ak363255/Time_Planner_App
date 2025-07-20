package com.example.presentation.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.entities.settings.ViewToggleStatus
import com.example.presentation.ui.theme.TimePlannerRes

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ViewToggle(
    modifier: Modifier = Modifier,
    isHideTitle : Boolean = false,
    status : ViewToggleStatus,
    onStatusChange : (ViewToggleStatus)-> Unit
){
    val title = when(status){
        ViewToggleStatus.EXPANDED -> TimePlannerRes.strings.expandedViewToggleTitle
        ViewToggleStatus.COMPACT -> TimePlannerRes.strings.compactViewToggleTitle
    }
    val icon = when(status){
        ViewToggleStatus.EXPANDED -> TimePlannerRes.icons.expandedViewIcon
        ViewToggleStatus.COMPACT -> TimePlannerRes.icons.compactViewIcon
    }
    TextButton(
        onClick = {onStatusChange(status)},
        modifier = modifier.height(40.dp)
    ) {
        BoxWithConstraints {
            if(maxWidth >= 129.dp && !isHideTitle){
                Row {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }else{
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }

}