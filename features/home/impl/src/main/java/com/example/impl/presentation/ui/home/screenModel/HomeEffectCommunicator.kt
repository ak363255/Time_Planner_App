package com.example.impl.presentation.ui.home.screenModel

import com.example.impl.presentation.ui.home.contract.HomeEffect
import com.example.utils.platform.communications.state.EffectCommunicator

interface HomeEffectCommunicator : EffectCommunicator<HomeEffect> {
    class Base: HomeEffectCommunicator, EffectCommunicator.Abstract<HomeEffect>()
}