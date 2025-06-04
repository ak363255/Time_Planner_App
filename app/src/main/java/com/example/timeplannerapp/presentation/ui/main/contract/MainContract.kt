package com.example.timeplannerapp.presentation.ui.main.contract

import com.example.presentation.ui.theme.material.ColorsUiType
import com.example.presentation.ui.theme.material.ThemeUiType
import com.example.presentation.ui.theme.tokens.LanguageUiType
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseViewState
import com.example.utils.platform.screenmodel.contract.EmptyUiEffect
import kotlinx.parcelize.Parcelize


@Parcelize
data class MainViewState(
    val language: LanguageUiType = LanguageUiType.DEFAULT,
    val theme: ThemeUiType = ThemeUiType.DEFAULT,
    val colors: ColorsUiType = ColorsUiType.PINK,
    val isEnableDynamicColors: Boolean = false,
    val secureMode: Boolean = false,
) : BaseViewState

sealed class MainEvent : BaseEvent {
    data class Init(val screenTarget: DeepLinkTarget) : MainEvent()
    data object NavigateToEditor : MainEvent()
}

sealed class MainEffect : EmptyUiEffect{
    data object NavigateToMain: MainEffect()
    data object NavigateToEditor: MainEffect()
}

sealed class MainAction : MainEffect(), BaseAction{
    object Navigate: MainAction()
    data class ChangeSettings(
        val language: LanguageUiType,
        val theme: ThemeUiType,
        val colors: ColorsUiType,
        val enableDynamicColors : Boolean,
        val secureMode : Boolean
    ): MainAction()
}