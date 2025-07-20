package com.example.presentation.ui.notifications

import android.content.Intent
import com.example.domain.entities.Template.RepeatTime
import com.example.domain.entities.schedules.NotificationTimeType
import java.util.Date

interface AlarmReceiverProvider {
    fun provideReceiverIntent(
        category:String,
        subCategory: String,
        icon:Int?,
        appIcon:Int,
        time: Date? = null,
        templateId:Int? = null,
        repeatTime: RepeatTime?= null,
        timeType : NotificationTimeType
        ): Intent
}