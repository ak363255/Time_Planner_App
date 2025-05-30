package com.example.utils.platform.screenmodel

import androidx.lifecycle.ViewModel
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseEvent
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import com.example.utils.platform.screenmodel.contract.BaseViewState

/**
 * Created by Amit on 30-05-2025.
 */
abstract class BaseViewModel<S:BaseViewState,E:BaseEvent,A:BaseAction,F:BaseUiEffect,D:ScreenDependencies>:ViewModel() {
}