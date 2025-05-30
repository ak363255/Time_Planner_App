package com.example.utils.platform.screenmodel

import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseViewState

interface Reducer<S:BaseViewState,A:BaseAction> {
    suspend fun reduce(action:A,currentState:S):S
}