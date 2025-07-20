package com.example.impl.domain.common

import com.example.impl.domain.entities.HomeFailures
import com.example.utils.wrappers.FlowEitherWrapper

interface HomeEitherWrapper : FlowEitherWrapper<HomeFailures>{
    class Base(errorHandler : HomeErrorHandler) : HomeEitherWrapper, FlowEitherWrapper.Abstract<HomeFailures>(errorHandler)
}