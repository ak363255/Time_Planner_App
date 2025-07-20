package com.example.presentation.ui.views

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarTitle(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    text  : String,
    subText : String? = null,
){
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            textAlign = textAlign,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge
        )
        if(subText != null){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subText,
                textAlign = textAlign,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}
@Composable
fun TopAppBarEmptyButton(modifier : Modifier = Modifier){
    Spacer(modifier = modifier.size(48.dp))
}

@Composable
fun TopAppBarButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    imageVector: ImageVector,
    imageDescription: String?,
    onButtonclick: () -> Unit,
    onDoubleButtonClick: (() -> Unit)? = null,
    onLongButtonClick: (()-> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
){

    ExtendedIconButton(
        enabled = enabled,
        modifier = modifier.size(48.dp),
        onClick = onButtonclick,
        onDoubleClick = onDoubleButtonClick,
        onLongClick = onLongButtonClick,
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

@Composable
fun TopAppBarButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    imagePainter: Painter,
    imageDescription: String?,
    onButtonclick: () -> Unit,
    onDoubleButtonClick: (() -> Unit)? = null,
    onLongButtonClick: (()-> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
){
    ExtendedIconButton(
        enabled = enabled,
        modifier = modifier.size(48.dp),
        onClick = onButtonclick,
        onDoubleClick = onDoubleButtonClick,
        onLongClick = onLongButtonClick,
        interactionSource = interactionSource
    ) {
        Icon(
            painter = imagePainter,
            contentDescription = imageDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}
