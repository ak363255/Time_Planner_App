package com.example.impl.presentation.ui.setting.contract

import android.net.Uri
import androidx.compose.ui.text.font.FontVariation
import com.example.impl.domain.common.SettingsFailures
import com.example.impl.presentation.models.SettingsUi
import com.example.impl.presentation.models.TaskSettingsUi
import com.example.impl.presentation.models.ThemeSettingsUi
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class SettingsViewState(
    val themeSettings : ThemeSettingsUi? = null,
    val taskSettings : TaskSettingsUi? = null,
    val failure  : SettingsFailures? = null,
    val isBackupLoading : Boolean = false,
) : BaseViewState

internal sealed class SettingsEvent : BaseEvent{
    data object Init : SettingsEvent()
    data object PressResetButton : SettingsEvent()
    data object PressClearDataButton : SettingsEvent()
    data object PressDonateButton : SettingsEvent()
    data class PressSaveBackupData(val url : Uri) : SettingsEvent()
    data class PressRestoreBackkupData(val uri : Uri): SettingsEvent()
    data class ChangedThemeSettings(val themeSettings : ThemeSettingsUi): SettingsEvent()
    data class ChangeTasksSettings(val taskSettings : TaskSettingsUi) : SettingsEvent()
}

 sealed class SettingsEffect : BaseUiEffect{
    data class ShowError(val failures : SettingsFailures) : SettingsEffect()
}

 sealed class SettingsAction : BaseAction{
    data class ShowLoadingBackup(val isLoading : Boolean) : SettingsAction()
    data class ChangeAllSettings(val settings : SettingsUi) : SettingsAction()
}