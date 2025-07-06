package com.example.impl.domain.common

import com.example.utils.functional.DomainFailures
import kotlinx.parcelize.Parcelize

@Parcelize
 sealed class SettingsFailures : DomainFailures{
    data class BackupError(val throwable: Throwable) : SettingsFailures()
    data class OtherError(val throwable: Throwable) : SettingsFailures()
}