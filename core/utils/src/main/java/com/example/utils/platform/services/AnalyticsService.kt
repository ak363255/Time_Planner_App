package com.example.utils.platform.services

import android.content.Context

interface AnalyticsService {
    fun trackEvent(name:String,eventParams : Map<String, String>)
    fun initializeService(context: Context)
}