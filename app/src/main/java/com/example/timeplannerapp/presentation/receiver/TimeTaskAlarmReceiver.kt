package com.example.timeplannerapp.presentation.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LightingColorFilter
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.domain.entities.Template.RepeatTimeType
import com.example.domain.entities.schedules.NotificationTimeType
import com.example.presentation.ui.notifications.TemplatesAlarmManager
import com.example.presentation.ui.theme.tokens.fetchCoreLanguage
import com.example.presentation.ui.theme.tokens.fetchCoreStrings
import com.example.timeplannerapp.MainActivity
import com.example.timeplannerapp.R
import com.example.timeplannerapp.presentation.mappers.mapToString
import com.example.utils.extensions.fetchLocale
import com.example.utils.functional.Constants
import com.example.utils.notifications.NotificationCreator
import com.example.utils.notifications.NotificationPriority
import com.example.utils.notifications.parametrers.NotificationDefaults

class TimeTaskAlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null || context == null)return
        if(intent.action == Constants.Alarm.ALARM_NOTIFICATION_ACTION){
            showNotificationByIntent(context,intent)
        }
    }
    private fun showNotificationByIntent(context: Context,intent: Intent){
        val notificationCreator = NotificationCreator.Base(context)
        val coreStrings = fetchCoreStrings(fetchCoreLanguage(context.fetchLocale().language))
        val timeType = intent.getStringExtra(Constants.Alarm.NOTIFICATION_TIME_TYPE)?.let { type ->
            NotificationTimeType.valueOf(type)
        } ?: NotificationTimeType.START_TASK

        val repeatTypeName = intent.getStringExtra(Constants.Alarm.REPEAT_TYPE)
        val repeatTime = repeatTypeName?.let { RepeatTimeType.valueOf(it) }
        val category = checkNotNull(intent.getStringExtra(Constants.Alarm.NOTIFICATION_CATEGORY))
        val subCategory = checkNotNull(intent.getStringExtra(Constants.Alarm.NOTIFICATION_SUBCATEGORY))
        val iconData = intent.getIntExtra(Constants.Alarm.NOTIFICATION_ICON, 0)
        val icon = if (iconData == 0) null else iconData
        val appIcon = checkNotNull(intent.getIntExtra(Constants.Alarm.APP_ICON, 0))
        val activityIntent = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = notificationCreator.createNotify(
            channelId = Constants.Notification.CHANNEL_ID_NEW,
            title = if (subCategory.isNotEmpty()) "$category, $subCategory" else category,
            text = timeType.mapToString(coreStrings),
            smallIcon = appIcon,
            largeIcon = icon?.let { largeIcon ->
                val drawable = ContextCompat.getDrawable(context, largeIcon)
                drawable?.colorFilter = LightingColorFilter(Color.DKGRAY, Color.DKGRAY)
                return@let drawable?.toBitmap()
            },
            autoCancel = true,
            priority = NotificationPriority.MAX,
            contentIntent = contentIntent,
            notificationDefaults = NotificationDefaults(true, true, true),
            color = ContextCompat.getColor(context, R.color.notification_icon),
        )
        notificationCreator.showNotify(notification, 0)
        if (repeatTime != null) {
            scheduleNextNotify(context, intent, timeType, repeatTime, category, subCategory, icon)
        }
    }

    private fun scheduleNextNotify(
        context: Context,
        intent: Intent,
        timeType: NotificationTimeType,
        repeatType: RepeatTimeType,
        category: String,
        subCategory: String?,
        icon: Int?,
    ){
        val receiverProvider = AlarmReceiverProviderImpl(context)
        val templatesAlarmManager = TemplatesAlarmManager.Base(context, receiverProvider)

    }

}