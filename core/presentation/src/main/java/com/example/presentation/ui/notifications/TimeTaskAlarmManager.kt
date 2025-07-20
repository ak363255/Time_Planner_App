package com.example.presentation.ui.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.domain.entities.categories.MainCategory
import com.example.domain.entities.categories.SubCategory
import com.example.domain.entities.schedules.NotificationTimeType
import com.example.domain.entities.schedules.TaskNotificationType
import com.example.domain.entities.schedules.TimeTask
import com.example.domain.entities.schedules.toTimeType
import com.example.presentation.ui.mappers.mapToIcon
import com.example.presentation.ui.mappers.mapToString
import com.example.presentation.ui.theme.tokens.fetchCoreIcons
import com.example.presentation.ui.theme.tokens.fetchCoreLanguage
import com.example.presentation.ui.theme.tokens.fetchCoreStrings
import com.example.utils.extensions.fetchCurrentLanguage
import com.example.utils.functional.Constants.App
import com.example.utils.managers.DateManager
import java.util.Date

interface TimeTaskAlarmManager {
    fun addOrUpdateNotifyAlarm(timeTask : TimeTask)
    fun deleteNotifyAlarm(timeTask  : TimeTask)

    class Base(
        private val context  : Context,
        private val receiverProvider : AlarmReceiverProvider,
        private val dateManager : DateManager
    ): TimeTaskAlarmManager{

        private val alarmManager: AlarmManager
            get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        private val currentTime: Date
            get() = dateManager.fetchCurrentDate()


        override fun addOrUpdateNotifyAlarm(timeTask: TimeTask) {
            timeTask.taskNotification.toTypes(timeTask.isEnableNotification).forEach { type ->
                val alarmIntent = createAlarmIntent(timeTask.category, timeTask.subCategory, type.toTimeType())
                val id = timeTask.key + type.idAmount
                val pendingAlarmIntent = createPendingAlarmIntent(alarmIntent, id.toInt())
                val triggerTime = type.fetchNotifyTrigger(timeTask.timeRange).time
                if (triggerTime > currentTime.time) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingAlarmIntent)
                }
            }
        }

        override fun deleteNotifyAlarm(timeTask: TimeTask) {
            TaskNotificationType.values().forEach { type ->
                val alarmIntent = createAlarmIntent(timeTask.category, timeTask.subCategory, type.toTimeType())
                val id = timeTask.key + type.idAmount
                val pendingAlarmIntent = createPendingAlarmIntent(alarmIntent, id.toInt())
                alarmManager.cancel(pendingAlarmIntent)
                pendingAlarmIntent.cancel()
            }
        }

        private fun createAlarmIntent(
            category : MainCategory,
            subCategory : SubCategory?,
            timeType : NotificationTimeType = NotificationTimeType.START_TASK
        ): Intent{
            val language = fetchCoreLanguage(context.fetchCurrentLanguage())
            val categoryName = category.let { it.default?.mapToString(fetchCoreStrings(language)) }
            val subCategoryName = subCategory?.name?:""
            val categoryIcon = category.default?.mapToIcon(fetchCoreIcons())
            val appIcon = fetchCoreIcons().logo
            return receiverProvider.provideReceiverIntent(
                subCategory = subCategoryName,
                icon = categoryIcon,
                appIcon = appIcon,
                timeType = timeType,
                category = categoryName ?:App.NAME
            )

        }
        private fun createPendingAlarmIntent(
            alarmIntent : Intent,
            requestId : Int
        ): PendingIntent{
            return PendingIntent.getBroadcast(
                context,
                requestId,
                alarmIntent,
                PendingIntent.FLAG_MUTABLE
            )

        }

    }
}