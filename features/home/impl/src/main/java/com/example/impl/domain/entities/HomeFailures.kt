package com.example.impl.domain.entities

import com.example.utils.functional.DomainFailures
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class HomeFailures : DomainFailures{
    object ShiftError : HomeFailures()
    object ImportanceError : HomeFailures()
    data class OtherError(val throwable : Throwable) : HomeFailures()

}