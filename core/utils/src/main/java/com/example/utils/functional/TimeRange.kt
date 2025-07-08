package com.example.utils.functional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date

@Parcelize
@Serializable
data class TimeRange(
    @Serializable(DateSerializer::class) val from : Date,
    @Serializable(DateSerializer::class) val to : Date,
): Parcelable