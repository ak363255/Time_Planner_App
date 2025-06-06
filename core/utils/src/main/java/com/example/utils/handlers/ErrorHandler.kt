package com.example.utils.handlers

/**
 * Created by Amit on 06-06-2025.
 */

interface ErrorHandler<E>{
    fun handle(throwable: Throwable):E
}