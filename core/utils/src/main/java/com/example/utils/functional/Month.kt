package com.example.utils.functional

enum class Month(val number : Int) {
    JANUARY(0),
    FEBRUARY(1),
    MARCH(2),
    APRIL(3),
    MAY(4),
    JUNE(5),
    JULY(6),
    AUGUST(7),
    SEPTEMBER(8),
    OCTOBER(9),
    NOVEMBER(10),
    DECEMBER(11);
    companion object {
        fun fetchByMonthNumber(month: Int): Month {
            val monthInstance = values().find { it.number == month }
            return monthInstance ?: throw IllegalArgumentException("Wrong month number: $month")
        }
    }
}
