package com.example.impl.presentation.ui.editor.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.impl.presentation.ui.editor.contract.EditorViewState
import com.example.impl.presentation.ui.editor.screenmodel.EditorScreenModel

@Composable
fun EditorScreen (
    screenModel : EditorScreenModel,
    initialState : EditorViewState = EditorViewState()
){
    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.Green)
    ){

    }

}