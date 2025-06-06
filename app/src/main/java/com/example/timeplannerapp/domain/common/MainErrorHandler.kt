package com.example.timeplannerapp.domain.common

import com.example.utils.handlers.ErrorHandler

/**
 * Created by Amit on 06-06-2025.
 */
interface MainErrorHandler : ErrorHandler<MainFailures>{
    class Base:MainErrorHandler{
        override fun handle(throwable: Throwable): MainFailures = when(throwable) {
            else -> MainFailures.OtherError(throwable)
        }

    }
}