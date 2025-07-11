package com.example.utils.extensions

import java.util.UUID

fun <T> List<List<T>>.extractAllItem() = mutableListOf<T>().apply {
    this@extractAllItem.forEach { addAll(it) }
}

fun Int?.toStringOrEmpty() = this?.toString() ?:""

fun generateUniqueKey() = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
fun String.substringOrFull(startIndex : Int,endIndex : Int) : String {
    return if(lastIndex>=endIndex)substring(startIndex,endIndex) else this
}