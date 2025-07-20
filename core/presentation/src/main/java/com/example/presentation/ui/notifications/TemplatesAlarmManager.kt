package com.example.presentation.ui.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.domain.entities.Template.RepeatTime
import com.example.domain.entities.Template.Template
import com.example.domain.entities.schedules.NotificationTimeType
import com.example.presentation.ui.mappers.mapToIcon
import com.example.presentation.ui.mappers.mapToString
import com.example.presentation.ui.theme.tokens.TimePlannerIcons
import com.example.presentation.ui.theme.tokens.TimePlannerStrings
import com.example.presentation.ui.theme.tokens.fetchCoreIcons
import com.example.presentation.ui.theme.tokens.fetchCoreLanguage
import com.example.presentation.ui.theme.tokens.fetchCoreStrings
import com.example.utils.extensions.fetchCurrentLanguage
import java.util.Date
import kotlin.text.category

interface TemplatesAlarmManager {

    fun addOrUpdateNotifyAlarm(template: Template, repeatTime: RepeatTime)

    fun addRawNotifyAlarm(
        templateId: Int,
        timeType: NotificationTimeType,
        repeatTime: RepeatTime,
        time: Date,
        category: String,
        subCategory: String?,
        icon: Int?,
    )

    fun deleteNotifyAlarm(template: Template, repeatTime: RepeatTime)
    class Base(
        private val context: Context,
        private val receiverProvider: AlarmReceiverProvider,
    ) :TemplatesAlarmManager {

        private val alarmManager: AlarmManager
            get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        private val coreIcons: TimePlannerIcons
            get() = fetchCoreIcons()

        private val coreString: TimePlannerStrings
            get() = fetchCoreStrings(fetchCoreLanguage(context.fetchCurrentLanguage()))

        override fun addOrUpdateNotifyAlarm(
            template: Template,
            repeatTime: RepeatTime
        )=  addRawNotifyAlarm(
        templateId = template.templateId,
        timeType = NotificationTimeType.START_TASK,
        repeatTime = repeatTime,
        time = template.startTime,
        category = template.category.let { it.default?.mapToString(coreString) ?: it.customName } ?: "",
        subCategory = template.subCategory?.name,
        icon = template.category.default?.mapToIcon(coreIcons),
        )

        override fun addRawNotifyAlarm(
            templateId: Int,
            timeType: NotificationTimeType,
            repeatTime: RepeatTime,
            time: Date,
            category: String,
            subCategory: String?,
            icon: Int?
        ) {
            val id = templateId + repeatTime.key
            val alarmIntent = receiverProvider.provideReceiverIntent(
                category = category,
                subCategory = subCategory.orEmpty(),
                icon = icon,
                appIcon = fetchCoreIcons().logo,
                time = time,
                templateId = templateId,
                repeatTime = repeatTime,
                timeType = timeType,
            )
            val pendingAlarmIntent = createPendingAlarmIntent(alarmIntent, id)
            val triggerTime = repeatTime.nextDate(time)

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime.time, pendingAlarmIntent)
        }

        override fun deleteNotifyAlarm(
            template: Template,
            repeatTime: RepeatTime
        ) {
            val id = template.templateId + repeatTime.key
            val alarmIntent = receiverProvider.provideReceiverIntent(
                category = template.category.let { it.default?.mapToString(coreString) ?: it.customName } ?: "",
                subCategory = template.subCategory?.name.orEmpty(),
                icon = template.category.default?.mapToIcon(coreIcons),
                appIcon = fetchCoreIcons().logo,
                time = template.startTime,
                templateId = template.templateId,
                repeatTime = repeatTime,
                timeType = NotificationTimeType.START_TASK,
            )
            val pendingAlarmIntent = createPendingAlarmIntent(alarmIntent, id)

            alarmManager.cancel(pendingAlarmIntent)
            pendingAlarmIntent.cancel()
        }

        private fun createPendingAlarmIntent(
            alarmIntent: Intent,
            requestId: Int,
        ) = PendingIntent.getBroadcast(
            context,
            requestId,
            alarmIntent,
            PendingIntent.FLAG_MUTABLE,
        )

    }


}