package com.example.impl.presentation.theme.tokens

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.impl.R

data class HomeIcons(
    val nextDate: Int,
    val previousDate: Int,
    val more: Int,
    val add: Int,
    val remove: Int,
    val calendar: Int,
    val menu: Int,
    val check: Int,
    val notFound: Int,
    val category: Int,
    val subCategory: Int,
    val startTime: Int,
    val endTime: Int,
    val notification: Int,
    val info: Int,
    val priority: Int,
    val cancel: Int,
    val statistics: Int,
    val duration: Int,
    val time: Int,
    val repeat: Int,
    val updateRepeat: Int,
    val repeatVariant: Int,
    val turnOffRepeat: Int,
    val stop: Int,
    val start: Int,
    val notes: Int,
    val schedule: Int,
    val completedTask: Int,
    val unexecutedTask: Int,
    val offNotifications: Int,
)

internal val baseHomeIcons = HomeIcons(
    nextDate = R.drawable.ic_next,
    previousDate = R.drawable.ic_previous,
    more = R.drawable.ic_more,
    add = R.drawable.ic_add,
    remove = R.drawable.ic_remove,
    calendar = R.drawable.ic_calendar,
    menu = R.drawable.ic_menu,
    check = R.drawable.ic_check,
    notFound = R.drawable.ic_not_found,
    category = R.drawable.ic_category,
    subCategory = R.drawable.ic_subcategory,
    startTime = R.drawable.ic_start_time,
    endTime = R.drawable.ic_end_time,
    notification = R.drawable.ic_notification,
    info = R.drawable.ic_info,
    priority = R.drawable.ic_priority_high,
    cancel = R.drawable.ic_cancel,
    statistics = R.drawable.ic_charts,
    duration = R.drawable.ic_timer,
    time = R.drawable.ic_time,
    repeat = R.drawable.ic_repeat,
    updateRepeat = R.drawable.ic_update_repeat,
    repeatVariant = R.drawable.ic_repeat_variant,
    turnOffRepeat = R.drawable.ic_off_repeat,
    stop = R.drawable.ic_stop,
    start = R.drawable.ic_play,
    notes = R.drawable.ic_notes,
    schedule = R.drawable.ic_schedule,
    completedTask = R.drawable.ic_complete_task,
    unexecutedTask = R.drawable.ic_not_complete_task,
    offNotifications = R.drawable.ic_bell_off,
)

internal val LocalHomeIcons = staticCompositionLocalOf<HomeIcons> {
    error("Home Icons is not provided")
}

internal fun fetchHomeIcons() = baseHomeIcons
