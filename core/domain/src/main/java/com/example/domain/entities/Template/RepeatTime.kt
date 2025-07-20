package com.example.domain.entities.Template

import android.os.Parcelable
import com.example.utils.extensions.fetchDay
import com.example.utils.extensions.fetchDayNumberByMax
import com.example.utils.extensions.fetchMonth
import com.example.utils.extensions.fetchWeekDay
import com.example.utils.extensions.fetchWeekNumber
import com.example.utils.extensions.setTimeWithoutDate
import com.example.utils.functional.Month
import com.example.utils.functional.WeekDay
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.Calendar
import java.util.Date

@Parcelize
sealed class RepeatTime : Parcelable{
    abstract val repeatType : RepeatTimeType
    abstract val key : Int

    @Serializable
    data class WeekDays(val day : WeekDay) : RepeatTime(){
        @IgnoredOnParcel
        override val repeatType: RepeatTimeType = RepeatTimeType.WEEK_DAY

        @IgnoredOnParcel
        override val key: Int
            get() =  day.number
    }

    @Serializable
    data class WeekDayInMonth(val day : WeekDay,val weekNumber : Int) : RepeatTime(){

        @IgnoredOnParcel
        override val repeatType: RepeatTimeType
            get() = RepeatTimeType.WEEK_DAY_IN_MONTH

        @IgnoredOnParcel
        override val key: Int
            get() = day.number + weekNumber
    }

    @Serializable
    data class MonthDay(val dayNumber: Int) : RepeatTime() {

        @IgnoredOnParcel
        override val repeatType = RepeatTimeType.MONTH_DAY

        @IgnoredOnParcel
        override val key = dayNumber
    }

    @Serializable
    data class YearDay(val month: Month, val dayNumber: Int) : RepeatTime() {

        @IgnoredOnParcel
        override val repeatType = RepeatTimeType.YEAR_DAY

        @IgnoredOnParcel
        override val key = month.number + dayNumber
    }

    fun checkDateIsRepeat(date: Date) = when (this) {
        is WeekDays -> date.fetchWeekDay() == day
        is WeekDayInMonth -> date.fetchWeekDay() == day && date.fetchWeekNumber() == weekNumber
        is MonthDay -> date.fetchDayNumberByMax(dayNumber) == dayNumber
        is YearDay -> date.fetchDayNumberByMax(dayNumber) == dayNumber && date.fetchMonth() == month
    }

    fun nextDate(startTime: Date, current: Date = Date()): Date {
        val calendar = Calendar.getInstance()
        val firstDay = calendar.firstDayOfWeek
        when (this) {
            is RepeatTime.WeekDays -> {
                calendar.time = current
                if (current.fetchWeekDay().priorityByFirstDayOfWeek(firstDay) >=
                    day.priorityByFirstDayOfWeek(firstDay)
                ) {
                    calendar.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1)
                }
                calendar.set(Calendar.DAY_OF_WEEK, day.number)
            }

            is RepeatTime.MonthDay -> {
                calendar.time = current
                if (current.fetchDay() >= dayNumber) {
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                    calendar.add(Calendar.MONTH, 1)
                }
                calendar.set(Calendar.DAY_OF_MONTH, dayNumber)
            }

            is RepeatTime.YearDay -> {
                calendar.time = current
                if (current.fetchMonth().number >= month.number && current.fetchDay() > dayNumber) {
                    calendar.add(Calendar.YEAR, 1)
                }
                calendar.set(Calendar.MONTH, month.number)
                calendar.set(Calendar.DAY_OF_MONTH, dayNumber)
            }

            is RepeatTime.WeekDayInMonth -> {
                calendar.time = current
                if (current.fetchWeekNumber() >= weekNumber &&
                    current.fetchWeekDay().priorityByFirstDayOfWeek(firstDay) > day.priorityByFirstDayOfWeek(firstDay)
                ) {
                    calendar.add(Calendar.MONTH, 1)
                }
                calendar.set(Calendar.DAY_OF_WEEK, day.number)
                calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekNumber)
            }
        }
        calendar.setTimeWithoutDate(startTime)
        return calendar.time
    }
}

