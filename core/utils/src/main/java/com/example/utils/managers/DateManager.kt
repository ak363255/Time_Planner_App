package com.example.utils.managers

import com.example.utils.extensions.setEndDay
import com.example.utils.extensions.setStartDay
import com.example.utils.extensions.toMinutes
import java.util.Calendar
import java.util.Date

interface DateManager {
    fun fetchCurrentDate(): Date
    fun fetchBeginningCurrentDay(): Date
    fun fetchEndCurrentDay(): Date
    fun calculateLeftTime(endTime:Date): Long
    fun calculateProgress(startTime : Date , endTime : Date): Float
    fun setCurrentHMS(date:Date): Date

    class Base() : DateManager{
        override fun fetchCurrentDate(): Date = checkNotNull(Calendar.getInstance().time)

        override fun fetchBeginningCurrentDay(): Date {
            val currentCalendar = Calendar.getInstance()
            return currentCalendar.setStartDay().time
        }

        override fun fetchEndCurrentDay(): Date {
            val currentCalendar = Calendar.getInstance()
            return currentCalendar.setEndDay().time
        }

        override fun calculateLeftTime(endTime: Date): Long {
            val currentDate = fetchCurrentDate()
            return endTime.time - currentDate.time
        }

        override fun calculateProgress(
            startTime: Date,
            endTime: Date
        ): Float {
            val currentTime = fetchCurrentDate().time
            val pastTime = ((currentTime - startTime.time)).toMinutes().toFloat()
            val duration = ((endTime.time - startTime.time)).toMinutes().toFloat()
            val progress = (pastTime/duration).coerceIn(0f,1f)
            return progress
        }

        override fun setCurrentHMS(date: Date): Date {
            val currentCalendar = Calendar.getInstance()
            val targetCalendar = Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY,currentCalendar.get(Calendar.HOUR_OF_DAY))
                set(Calendar.MINUTE,currentCalendar.get(Calendar.MINUTE))
                set(Calendar.SECOND,currentCalendar.get(Calendar.SECOND))
                set(Calendar.MILLISECOND,currentCalendar.get(Calendar.MILLISECOND))
            }
            return targetCalendar.time
        }

    }

}