package com.example.presentation.ui.views

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.presentation.ui.theme.TimePlannerRes

@Composable
fun <Item : BottomBarItem> BottomNavigationBar(
    modifier: Modifier,
    selectedItem: Item,
    items: Array<Item>,
    showLabel: Boolean,
    onItemSelected: (Item) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = TimePlannerRes.elevation.levelZero
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemSelected.invoke(item) },
                icon = {
                    BottomBarIcon(
                        selected = selectedItem == item,
                        enabledIcon = painterResource(id = item.enabledIcon),
                        disabledIcon = painterResource(id = item.disabledIcon),
                        description = item.label
                    )
                },
                label = if (showLabel) {
                    {
                        BottomBarLabel(
                            selected = selectedItem == item, title = item.label
                        )

                    }
                } else null,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )

            )

        }
    }


}

@Composable
fun BottomBarIcon(
    selected: Boolean,
    enabledIcon: Painter,
    disabledIcon: Painter,
    description: String
) {
    Icon(
        painter = if (selected) enabledIcon else disabledIcon,
        contentDescription = description,
        tint = when (selected) {
            true -> MaterialTheme.colorScheme.onSecondaryContainer

            false -> MaterialTheme.colorScheme.onSurfaceVariant

        }
    )


}

@Composable
fun BottomBarLabel(
    selected: Boolean,
    title: String
) {
    Text(
        text = title,
        color = when (selected) {
            true -> MaterialTheme.colorScheme.onSurface
            false -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        style = MaterialTheme.typography.labelMedium
    )
}


interface BottomBarItem {
    val label: String @Composable get
    val enabledIcon: Int @Composable get
    val disabledIcon: Int @Composable get
}