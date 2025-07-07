package com.example.timeplannerapp.presentation.ui.main.contract

import com.example.utils.platform.screenmodel.contract.BaseRoute
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


sealed class MainRoute : BaseRoute
{
    @Serializable
    @Parcelize
    data object Splash : MainRoute()
    @Parcelize
    @Serializable
    data object Home : MainRoute()
}
