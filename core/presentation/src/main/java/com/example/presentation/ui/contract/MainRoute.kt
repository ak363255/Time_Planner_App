package com.example.presentation.ui.contract

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
    @Parcelize
    @Serializable
    data class NavigateToEditorCreator(val timeTask: String) : MainRoute()
}
