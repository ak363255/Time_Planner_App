package com.example.impl.domain.common

import com.example.impl.domain.entities.HomeFailures
import com.example.impl.domain.entities.TimeTaskImportanceException
import com.example.utils.functional.TimeShiftException
import com.example.utils.handlers.ErrorHandler

interface HomeErrorHandler : ErrorHandler<HomeFailures>{
    class Base: HomeErrorHandler{
        override fun handle(throwable: Throwable): HomeFailures = when(throwable){
            is TimeShiftException -> HomeFailures.ShiftError
            is TimeTaskImportanceException -> HomeFailures.ImportanceError
            else -> HomeFailures.OtherError(throwable)

        }

    }
}