package com.example.data.mappers.template

import com.example.data.models.template.RepeatTimeEntity
import com.example.domain.entities.Template.RepeatTime
import com.example.domain.entities.Template.RepeatTimeType

fun RepeatTime.mapToData(templateId: Int) = when (this) {
    is RepeatTime.MonthDay -> mapToData(templateId)
    is RepeatTime.WeekDays -> mapToData(templateId)
    is RepeatTime.WeekDayInMonth -> mapToData(templateId)
    is RepeatTime.YearDay -> mapToData(templateId)
}

fun RepeatTimeEntity.mapToDomain() = when (type) {
    RepeatTimeType.WEEK_DAY -> mapToWeekDayRepeatTime()
    RepeatTimeType.WEEK_DAY_IN_MONTH -> mapToWeekDayInMonthRepeatTime()
    RepeatTimeType.MONTH_DAY -> mapToMonthRepeatTime()
    RepeatTimeType.YEAR_DAY -> mapToYearRepeatTime()
}

fun RepeatTimeEntity.mapToWeekDayRepeatTime() = RepeatTime.WeekDays(
    day = checkNotNull(day),
)
fun RepeatTimeEntity.mapToWeekDayInMonthRepeatTime() = RepeatTime.WeekDayInMonth(
    day = checkNotNull(day),
    weekNumber = checkNotNull(weekNumber),
)

fun RepeatTimeEntity.mapToMonthRepeatTime() = RepeatTime.MonthDay(
    dayNumber = checkNotNull(dayNumber),
)

fun RepeatTimeEntity.mapToYearRepeatTime() = RepeatTime.YearDay(
    month = checkNotNull(month),
    dayNumber = checkNotNull(dayNumber),
)

fun RepeatTime.WeekDays.mapToData(templateId: Int) = RepeatTimeEntity(
    templateId = templateId,
    type = RepeatTimeType.WEEK_DAY,
    day = day,
)

fun RepeatTime.WeekDayInMonth.mapToData(templateId: Int) = RepeatTimeEntity(
    templateId = templateId,
    type = RepeatTimeType.WEEK_DAY_IN_MONTH,
    day = day,
    weekNumber = weekNumber,
)

fun RepeatTime.MonthDay.mapToData(templateId: Int) = RepeatTimeEntity(
    templateId = templateId,
    type = RepeatTimeType.MONTH_DAY,
    dayNumber = dayNumber,
)

fun RepeatTime.YearDay.mapToData(templateId: Int) = RepeatTimeEntity(
    templateId = templateId,
    type = RepeatTimeType.YEAR_DAY,
    month = month,
    dayNumber = dayNumber,
)