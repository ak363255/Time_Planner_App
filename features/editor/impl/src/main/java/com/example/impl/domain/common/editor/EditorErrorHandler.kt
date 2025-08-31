package com.example.impl.domain.common.editor

import com.example.impl.domain.entities.EditorFailures
import com.example.utils.handlers.ErrorHandler
import com.example.utils.managers.TimeOverlayException

interface EditorErrorHandler : ErrorHandler<EditorFailures>{
    class Base(): EditorErrorHandler{
        override fun handle(throwable: Throwable): EditorFailures = when(throwable){
            is TimeOverlayException -> EditorFailures.TimeOverlayError(
                startOverlay = throwable.startOverlay,
                endOverlay = throwable.endOverlay
            )
            else -> EditorFailures.OtherError(throwable)
        }

    }
}