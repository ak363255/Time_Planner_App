package com.example.utils.extensions

import com.example.utils.functional.Constants
import com.example.utils.functional.TimeRange
import java.math.RoundingMode
import java.time.Month
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.text.toFloat
import kotlin.times
import kotlin.to
import kotlin.toString

fun Date.shiftDay(amount : Int,locale : Locale = Locale.getDefault()): Date{
    val calendar = Calendar.getInstance(locale).also {
        it.time = this@shiftDay
        startThisDay()
        it.add(Calendar.DAY_OF_YEAR,amount)
    }
    return calendar.time
}

fun Date.shiftHours(amount : Int,locale : Locale = Locale.getDefault()): Date{
    val calendar = Calendar.getInstance(locale).also {
        it.time = this@shiftHours
        it.add(Calendar.HOUR_OF_DAY,amount)
    }
    return calendar.time
}

fun Date.shiftMinutes(amount : Int,locale : Locale = Locale.getDefault()): Date{
    val calendar = Calendar.getInstance(locale).also {
        it.time = this@shiftMinutes
        it.add(Calendar.MINUTE,amount)
    }
    return calendar.time
}

fun Date.shiftMillis(amount: Int, locale: Locale = Locale.getDefault()): Date {
    val calendar = Calendar.getInstance(locale).also {
        it.time = this@shiftMillis
        it.add(Calendar.MILLISECOND, amount)
    }
    return calendar.time
}

fun Date.isCurrentDay(date:Date = Date()): Boolean{
    val currentDate = Calendar.getInstance().apply { time  = date}.get(Calendar.DAY_OF_YEAR)
    val compareDate = Calendar.getInstance().apply { time = this@isCurrentDay }.get(Calendar.DAY_OF_YEAR)
    return currentDate == compareDate
}

fun Date.isCurrentMonth(date: Date = Date()): Boolean {
    val currentMonth = Calendar.getInstance().apply { time = date }.get(Calendar.MONTH)
    val compareMonth = Calendar.getInstance().apply { time = this@isCurrentMonth }.get(Calendar.MONTH)

    return currentMonth == compareMonth
}

fun Date.compareByHoursAndMinutes(compareDate: Date): Boolean {
    val firstCalendar = Calendar.getInstance().apply { time = this@compareByHoursAndMinutes }
    val secondCalendar = Calendar.getInstance().apply { time = compareDate }
    val hoursEquals = firstCalendar.get(Calendar.HOUR_OF_DAY) == secondCalendar.get(Calendar.HOUR_OF_DAY)
    val minutesEquals = firstCalendar.get(Calendar.MINUTE) == secondCalendar.get(Calendar.MINUTE)

    return hoursEquals && minutesEquals
}
fun Date.endThisDay(): Date {
    val calendar = Calendar.getInstance().apply { time = this@endThisDay }
    return calendar.setEndDay().time
}

fun Date.endOfCurrentMonth(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@endOfCurrentMonth
        set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
    }
    return calendar.time
}

fun Calendar.setTimeWithoutDate(targetTime: Date): Calendar {
    val timeCalendar = Calendar.getInstance().apply { time = targetTime }
    set(Calendar.MILLISECOND, timeCalendar.get(Calendar.MILLISECOND))
    set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND))
    set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
    set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
    return this
}

fun Calendar.setHoursAndMinutes(hour: Int, minute: Int) = this.apply {
    set(Calendar.HOUR_OF_DAY, hour)
    set(Calendar.MINUTE, minute)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun Calendar.setPreviousMonth() = this.apply {
    val current = get(Calendar.MONTH)
    val previous = if (current == 0) 11 else current - 1
    set(Calendar.MONTH, previous)
}

fun Date.changeDay(date: Date): Date {
    val changedDateCalendar = Calendar.getInstance().apply {
        time = this@changeDay
    }
    val newDateCalendar = Calendar.getInstance().apply {
        time = date.startThisDay()
        set(Calendar.HOUR_OF_DAY, changedDateCalendar.get(Calendar.HOUR_OF_DAY))
        set(Calendar.MINUTE, changedDateCalendar.get(Calendar.MINUTE))
        set(Calendar.SECOND, changedDateCalendar.get(Calendar.SECOND))
        set(Calendar.MILLISECOND, changedDateCalendar.get(Calendar.MILLISECOND))
    }
    return newDateCalendar.time
}
fun duration(start: Date, end: Date): Long {
    return abs(end.time - start.time)
}
fun Date.isNotZeroDifference(end: Date): Boolean {
    return duration(this, end) > 0L
}
fun duration(timeRange: TimeRange): Long {
    return abs(timeRange.to.time - timeRange.from.time)
}
fun Long.mapToDate(): Date {
    val calendar = Calendar.getInstance().also {
        it.timeInMillis = this
    }
    return calendar.time
}
fun Long.toSeconds(): Long {
    return this / Constants.Date.MILLIS_IN_SECONDS
}

fun Long.toMinutes(): Long {
    return toSeconds() / Constants.Date.SECONDS_IN_MINUTE
}
fun Long.toMinutesInHours(): Long {
    val hours = toHorses()
    val minutes = toMinutes()
    return minutes - hours * Constants.Date.MINUTES_IN_HOUR
}
fun Long.toHorses(): Long {
    return toMinutes() / Constants.Date.MINUTES_IN_HOUR
}

fun Long.toDays(): Long {
    return toHorses() / Constants.Date.HOURS_IN_DAY
}

fun Long.daysToMillis(): Long {
    val hours = this * Constants.Date.HOURS_IN_DAY
    val minutes = hours * Constants.Date.MINUTES_IN_HOUR
    val seconds = minutes * Constants.Date.SECONDS_IN_MINUTE
    return seconds * Constants.Date.MILLIS_IN_SECONDS
}

fun Int.minutesToMillis(): Long {
    return this * Constants.Date.MILLIS_IN_MINUTE
}

fun Int.hoursToMillis(): Long {
    return this * Constants.Date.MILLIS_IN_HOUR
}
fun Long.toMinutesOrHoursString(minutesSymbol: String, hoursSymbol: String): String {
    val minutes = this.toMinutes()
    val hours = this.toHorses()

    return if (minutes == 0L) {
        Constants.Date.MINUTES_FORMAT.format("1", minutesSymbol)
    } else if (minutes in 1L..59L) {
        Constants.Date.MINUTES_FORMAT.format(minutes.toString(), minutesSymbol)
    } else if (minutes > 59L && (minutes % 60L) != 0L) {
        Constants.Date.HOURS_AND_MINUTES_FORMAT.format(
            hours.toString(),
            hoursSymbol,
            toMinutesInHours().toString(),
            minutesSymbol,
        )
    } else {
        Constants.Date.HOURS_FORMAT.format(hours.toString(), hoursSymbol)
    }
}

fun Long.toMinutesAndHoursString(minutesSymbol: String, hoursSymbol: String): String {
    val minutes = this.toMinutes()
    val hours = this.toHorses()

    return Constants.Date.HOURS_AND_MINUTES_FORMAT.format(
        hours.toString(),
        hoursSymbol,
        (minutes - hours * Constants.Date.MINUTES_IN_HOUR).toString(),
        minutesSymbol,
    )
}

fun Long.toDaysString(dayTitle: String): String {
    val horses = this.toHorses()
    val rawDays = horses / Constants.Date.HOURS_IN_DAY.toFloat()
    val days = rawDays.toBigDecimal().setScale(0, RoundingMode.UP).toInt()
    return if (days > 0) "< $days $dayTitle" else "$days $dayTitle"
}

fun Date.setSecond(second: Int): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@setSecond
        set(Calendar.SECOND, second)
        set(Calendar.MILLISECOND, 0)
    }

    return calendar.time
}
fun Date.setZeroSecond(): Date {
    return setSecond(0)
}

fun TimeRange.isIncludeTime(time: Date): Boolean {
    return time >= this.from && time <= this.to
}


fun Calendar.setEndDay() = this.apply {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 59)
    set(Calendar.MILLISECOND, 59)
}
fun TimeRange.toDaysTitle(): String {
    val calendar = Calendar.getInstance()
    val dayStart = calendar.apply { time = from }.get(Calendar.DAY_OF_MONTH)
    val dayEnd = calendar.apply { time = to }.get(Calendar.DAY_OF_MONTH)
    return "$dayStart-$dayEnd"
}

fun TimeRange.toMonthTitle(): String {
    val calendar = Calendar.getInstance()
    val monthStart = calendar.apply { time = from }.get(Calendar.MONTH) + 1
    val monthEnd = calendar.apply { time = to }.get(Calendar.MONTH) + 1
    return "$monthStart-$monthEnd"
}
fun countWeeksByDays(days: Int): Int {
    return ceil(days.toDouble() / Constants.Date.DAYS_IN_WEEK).toInt()
}

fun countMonthByDays(days: Int): Int {
    return ceil(days.toDouble() / Constants.Date.DAYS_IN_MONTH).toInt()
}






fun Date.startThisDay():Date{
    val calendar = Calendar.getInstance().apply {
        time = this@startThisDay
    }
    return calendar.setStartDay().time
}

fun Calendar.setStartDay() = this.apply {
    set(Calendar.HOUR_OF_DAY,0)
    set(Calendar.MINUTE,0)
    set(Calendar.SECOND,0)
    set(Calendar.MILLISECOND,0)
}

fun Date.fetchMonth(): Month {
    val calendar = Calendar.getInstance().apply { time = this@fetchMonth }
    val monthNumber = calendar.get(Calendar.MONTH)
   // return Month.fetchByMonthNumber(monthNumber)  TODO
}