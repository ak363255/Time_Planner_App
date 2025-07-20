package com.example.utils.managers

import com.example.utils.functional.TimeRange
import java.util.Date

interface TimeOverlayManager {
    fun isOverlay(current : TimeRange,allTimeRanges: List<TimeRange>): TimeOverlayResult
    class Base : TimeOverlayManager{
        override fun isOverlay(
            current: TimeRange,
            allTimeRanges: List<TimeRange>
        ): TimeOverlayResult {
            var leftBorder : Date? = null
            var rightBorder : Date? = null
            var startError = false
            var endError = false
            allTimeRanges.forEach { newRange ->
                if(newRange.to < current.to && (leftBorder ==null || newRange.to > leftBorder)){
                    if(newRange.to > current.from){
                        leftBorder = newRange.to
                        startError = true
                    }
                }

                if(newRange.from >= current.from && (rightBorder==null || newRange.from > rightBorder)){
                    if(newRange.from < current.to){
                        rightBorder = newRange.from
                        endError = true
                    }
                }

                if(current.from > newRange.from && current.to < newRange.to){
                    startError = true
                    endError = true
                }
            }
            if(leftBorder != null && rightBorder != null){
                if(leftBorder!!.time > rightBorder!!.time){
                    rightBorder = current.to
                }else if(rightBorder!!.time > current.to.time){
                    rightBorder = current.to
                }
            }
            return TimeOverlayResult(
                isOverlay = startError || endError,
                leftTimeBorder = leftBorder,
                rightTimeBorder = rightBorder
            )
        }

    }

}

data class TimeOverlayResult(
    val isOverlay : Boolean,
    val leftTimeBorder : Date?,
    val rightTimeBorder : Date?
)

data class TimeOverlayException(val startOverlay : Date?,val endOverlay : Date?) : Exception()