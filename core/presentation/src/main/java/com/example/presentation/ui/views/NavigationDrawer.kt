package com.example.presentation.ui.views

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.utils.managers.DrawerItem
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun <Item: DrawerItem> DrawerItems(
    modifier : Modifier = Modifier,
    selectedItemIndex : Int,
    items : Array<Item>,
    isAlwaysSelected : Boolean = false,
    onItemSelected : (Item) -> Unit
){
  items.forEachIndexed { index,item ->
      DrawerItem(
          modifier = modifier.height(54.dp).padding(end = 12.dp),
          selected = index == selectedItemIndex,
          onClick = {
              if(isAlwaysSelected || selectedItemIndex != index) onItemSelected.invoke(item)
          },
          icon = {
              Icon(painter = painterResource(item.icon), contentDescription = null)
          },
          label = {
              Text(
                  text = item.title,
                  color = MaterialTheme.colorScheme.onSurface,
                  style = MaterialTheme.typography.labelLarge
              )
          },
          colors = NavigationDrawerItemDefaults.colors(
              selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
              selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
              selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
          )
      )
  }

}

@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    selected : Boolean,
    onClick : () -> Unit,
    icon : (@Composable ()-> Unit)? = null,
    label : (@Composable ()-> Unit),
    badge : (@Composable ()-> Unit)? = null,
    shape : Shape = RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp),
    colors : NavigationDrawerItemColors = NavigationDrawerItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
){
    Surface(
        modifier = modifier.height(50.dp).fillMaxWidth(),
        selected = selected,
        onClick = onClick,
        shape = shape,
        color = colors.containerColor(selected).value,
        interactionSource = interactionSource
    ) {
        Row(
            Modifier.padding(start = 16.dp,end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(icon != null){
                val iconColor = colors.iconColor(selected).value
                CompositionLocalProvider(LocalContentColor provides iconColor, content = icon)
            }
            Box(Modifier.weight(1f)){
                val labelColor = colors.textColor(selected).value
                CompositionLocalProvider(LocalContentColor provides labelColor, content = label)
            }

            if(badge != null){
                Spacer(Modifier.width(12.dp))
                val badgeColor = colors.badgeColor(selected).value
                CompositionLocalProvider(LocalContentColor provides badgeColor, content = badge)
            }
        }
    }

}

@Composable
fun DrawerLogoSection(
    modifier : Modifier = Modifier,
    logoIcon : Painter,
    description : String,
    color : Color = MaterialTheme.colorScheme.onSurface
){
    Row(
        modifier = modifier.padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = logoIcon,
            contentDescription = description,
            tint = color
        )
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
    }
}