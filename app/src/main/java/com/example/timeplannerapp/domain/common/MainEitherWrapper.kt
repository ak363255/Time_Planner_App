package com.example.timeplannerapp.domain.common

import com.example.utils.handlers.ErrorHandler
import com.example.utils.wrappers.FlowEitherWrapper

/**
 * Created by Amit on 06-06-2025.
 */

interface MainEitherWrapper : FlowEitherWrapper<MainFailures>{
    class Base(
        errorHandler: MainErrorHandler
    ): MainEitherWrapper,FlowEitherWrapper.Abstract<MainFailures>(errorHandler)
}