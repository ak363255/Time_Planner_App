package com.example.utils.platform.screenmodel.work

import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseUiEffect

interface WorkResultHandler<A:BaseAction,F:BaseUiEffect>{
    suspend fun WorkResult<A,F>.handleWork()
    suspend fun FlowWorkResult<A,F>.collectAndHandleWork()
}
