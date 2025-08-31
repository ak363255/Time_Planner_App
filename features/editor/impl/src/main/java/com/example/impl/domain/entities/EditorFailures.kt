package com.example.impl.domain.entities

import com.example.utils.functional.DomainFailures
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
sealed class EditorFailures : DomainFailures{
    data class TimeOverlayError(val startOverlay : Date?,val endOverlay : Date?): EditorFailures()
    data class OtherError(val throwable : Throwable) : EditorFailures()

}