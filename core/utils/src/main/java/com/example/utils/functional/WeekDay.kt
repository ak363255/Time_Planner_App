package com.example.utils.functional

enum class WeekDay(val number: Int) {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);

    fun priorityByFirstDayOfWeek(startNumber: Int): Int {
        return if (number >= startNumber) {
            number - startNumber
        } else {
            (7 - startNumber) + number
        }
    }

    companion object {
        fun fetchByWeekDayNumber(week: Int): WeekDay {
            val weekInstance = WeekDay.entries.find { it.number == week }
            return weekInstance ?: throw IllegalArgumentException("Wrong week number: $week")
        }
    }
}