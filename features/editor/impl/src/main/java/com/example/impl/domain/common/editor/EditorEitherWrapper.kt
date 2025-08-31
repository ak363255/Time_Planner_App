package com.example.impl.domain.common.editor

import com.example.impl.domain.entities.EditorFailures
import com.example.utils.wrappers.EitherWrapper

interface EditorEitherWrapper : EitherWrapper<EditorFailures>{
    class Base(
        errorHandler : EditorErrorHandler
    ): EditorEitherWrapper, EitherWrapper.Abstract<EditorFailures>(errorHandler)
}