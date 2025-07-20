package com.example.utils.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.utils.notifications.parametrers.NotificationDefaults
import com.example.utils.notifications.parametrers.NotificatinStyles
import com.example.utils.notifications.parametrers.NotificationImportance
import com.example.utils.notifications.parametrers.NotificationProgress


interface NotificationCreator {
    fun createNotify(
        channelId : String,
        title : String,
        text : String,
        timeStamp : Long? = System.currentTimeMillis(),
        smallIcon:Int,
        largeIcon : Bitmap? = null,
        visibility : NotificationVisibility = NotificationVisibility.PUBLIC,
        priority : NotificationPriority = NotificationPriority.DEFAULT,
        actions : List<NotificationCompat.Action> = emptyList(),
        contentIntent : PendingIntent?= null,
        notificationDefaults : NotificationDefaults = NotificationDefaults(),
        autoCancel : Boolean = true,
        ongoing : Boolean = false,
        style : NotificatinStyles? = null,
        color :Int? = null,
        progress : NotificationProgress? = null,
    ): Notification

    fun showNotify(notification: Notification, notifyId: Int)
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotifyChannel(
        channelId: String,
        channelName: String,
        importance: NotificationImportance,
        defaults: NotificationDefaults,
    )
    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteNotifyChannel(channelId: String)

    class Base(
        private val context : Context
    ): NotificationCreator{
        private val notificationManager: NotificationManager
            get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        override fun createNotify(
            channelId: String,
            title: String,
            text: String,
            timeStamp: Long?,
            smallIcon: Int,
            largeIcon: Bitmap?,
            visibility: NotificationVisibility,
            priority: NotificationPriority,
            actions: List<NotificationCompat.Action>,
            contentIntent: PendingIntent?,
            notificationDefaults: NotificationDefaults,
            autoCancel: Boolean,
            ongoing: Boolean,
            style: NotificatinStyles?,
            color: Int?,
            progress: NotificationProgress?
        ): Notification {
            val builder = when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                true -> NotificationCompat.Builder(context, channelId)
                false -> NotificationCompat.Builder(context)
            }
            builder.apply {
                setContentTitle(title)
                setContentText(text)
                setVisibility(visibility.visibility)
                setPriority(priority.importance)
                if (timeStamp == null) setShowWhen(false) else setWhen(timeStamp)
                if (color != null) setColor(color)
                setSmallIcon(smallIcon)
                if (largeIcon != null) setLargeIcon(largeIcon)
                if (contentIntent != null) setContentIntent(contentIntent)
                setAutoCancel(autoCancel)
                setOngoing(ongoing)
                if (notificationDefaults.isVibrate) setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                if (notificationDefaults.isSound) {
                    setDefaults(NotificationCompat.DEFAULT_SOUND)
                }
                if (notificationDefaults.isLights) setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                if (progress != null) with(progress) { setProgress(max, value, isIndeterminate) }
                if (style != null) setStyle(style.style)
                actions.forEach { addAction(it) }
            }
            return builder.build()
        }

        override fun showNotify(notification: Notification, notifyId: Int) {
            notificationManager.notify(notifyId, notification)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun createNotifyChannel(
            channelId: String,
            channelName: String,
            importance: NotificationImportance,
            defaults: NotificationDefaults
        ) {
            val channel = NotificationChannel(channelId, channelName, importance.importance).apply {
                enableLights(defaults.isLights)
                enableVibration(defaults.isVibrate)
                vibrationPattern = longArrayOf(500, 500, 500)
            }
            notificationManager.createNotificationChannel(channel)

        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun deleteNotifyChannel(channelId: String) {
            val channel = notificationManager.getNotificationChannel(channelId)
            if (channel != null) notificationManager.deleteNotificationChannel(channelId)

        }

    }
}