package com.example.timeplannerapp.domain.common

import com.example.utils.functional.DomainFailures
import kotlinx.android.parcel.Parcelize

/**
 * Created by Amit on 06-06-2025.
 */
@Parcelize
sealed class MainFailures : DomainFailures{
    data class OtherError(val throwable : Throwable) : MainFailures()
}