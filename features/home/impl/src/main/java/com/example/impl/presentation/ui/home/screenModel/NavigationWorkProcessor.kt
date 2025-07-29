package com.example.impl.presentation.ui.home.screenModel

import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.schedules.TimeTask
import com.example.impl.domain.interactors.TemplatesInteractor
import com.example.impl.presentation.models.schedules.TimeTaskUi
import com.example.impl.presentation.ui.home.contract.HomeAction
import com.example.impl.presentation.ui.home.contract.HomeEffect
import com.example.utils.functional.TimeRange
import com.example.utils.platform.screenmodel.work.FlowWorkProcessor
import com.example.utils.platform.screenmodel.work.FlowWorkResult
import com.example.utils.platform.screenmodel.work.WorkCommand
import com.example.utils.platform.screenmodel.work.WorkResult
import java.util.Date

interface NavigationWorkProcessor :
    FlowWorkProcessor<NavigationWorkCommand, HomeAction, HomeEffect> {

    class Base(
        private val templateInteractor : TemplatesInteractor
    ): NavigationWorkProcessor{
        override suspend fun work(command: NavigationWorkCommand): FlowWorkResult<HomeAction, HomeEffect> = when(command) {
            is NavigationWorkCommand.NavigateToEditor -> {}
            is NavigationWorkCommand.NavigateToEditorCreator -> {}
            NavigationWorkCommand.NavigateToOverview -> {}
        }

    }
    private fun navigateToEditorCreator(date : Date,timeRange: TimeRange): WorkResult<HomeAction, HomeEffect>{
        val timeTask = TimeTask(date = date, category = MainCategory(), createdAt = Date(), timeRange = timeRange)

    }

}

sealed class NavigationWorkCommand : WorkCommand {
    object NavigateToOverview : NavigationWorkCommand()
    data class NavigateToEditor(val timeTask: TimeTaskUi) : NavigationWorkCommand()
    data class NavigateToEditorCreator(val currentDate: Date, val timeRange: TimeRange) :
        NavigationWorkCommand()
}