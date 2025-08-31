package com.example.impl.presentation.ui.editor.screenmodel

import com.example.impl.presentation.ui.editor.contract.EditorViewState
import com.example.utils.platform.communications.state.StateCommunicator

internal interface EditorStateCommunicator : StateCommunicator<EditorViewState>{
    class Base(): EditorStateCommunicator, StateCommunicator.Abstract<EditorViewState>(defaultState = EditorViewState())
}