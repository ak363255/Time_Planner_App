package com.example.timeplannerapp.presentation.ui.main.viewmodel

import com.example.timeplannerapp.presentation.ui.main.contract.MainEffect
import com.example.utils.platform.communications.state.EffectCommunicator

interface MainEffectCommunicator: EffectCommunicator<MainEffect>{
    class Base : MainEffectCommunicator, EffectCommunicator.Abstract<MainEffect>()
}