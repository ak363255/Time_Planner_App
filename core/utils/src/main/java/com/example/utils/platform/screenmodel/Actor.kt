package com.example.utils.platform.screenmodel

import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState

interface Actor<S:BaseViewState,E:BaseEvent,A:BaseAction,F:BaseUiEffect>{
}