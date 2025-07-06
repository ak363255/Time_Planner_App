package com.example.impl.domain.common

import com.example.utils.wrappers.FlowEitherWrapper

interface SettingsEitherWrapper : FlowEitherWrapper<SettingsFailures> {
    class Base(errorHandler: SettingsErrorHandler) : SettingsEitherWrapper,
        FlowEitherWrapper.Abstract<SettingsFailures>(errorHandler)
}