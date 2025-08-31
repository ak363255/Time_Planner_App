package com.example.impl.presentation.ui.editor.screenmodel

import com.example.impl.presentation.ui.editor.contract.EditorEffect
import com.example.utils.platform.communications.state.EffectCommunicator

interface EditorEffectCommunicator : EffectCommunicator<EditorEffect>{
    class Base(): EditorEffectCommunicator, EffectCommunicator.Abstract<EditorEffect>()
}