package com.example.timeplannerapp.data

import android.content.Context
import com.example.utils.platform.services.AnalyticsService

class AnalyticsServiceImpl : AnalyticsService {
    override fun trackEvent(
        name: String,
        eventParams: Map<String, String>
    ) {

    }

    override fun initializeService(context : Context) {
    }
}