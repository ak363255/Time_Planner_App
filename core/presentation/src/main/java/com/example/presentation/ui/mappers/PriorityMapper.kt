package com.example.presentation.ui.mappers

import com.example.domain.entities.schedules.TaskPriority
import com.example.presentation.ui.views.MonogramPriority

fun TaskPriority.mapToUi() = when(this){
    TaskPriority.STANDARD -> MonogramPriority.STANDARD
    TaskPriority.MEDIUM -> MonogramPriority.MEDIUM
    TaskPriority.MAX -> MonogramPriority.MAX
}