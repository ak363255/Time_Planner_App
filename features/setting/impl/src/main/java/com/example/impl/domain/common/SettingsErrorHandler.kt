package com.example.impl.domain.common

import com.example.utils.handlers.ErrorHandler
import java.io.IOException

class SettingsErrorHandler : ErrorHandler<SettingsFailures> {
    override fun handle(throwable: Throwable): SettingsFailures = when(throwable){
        is IOException -> SettingsFailures.BackupError(throwable)
        else -> SettingsFailures.OtherError(throwable)
    }
}