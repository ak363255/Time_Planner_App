package com.example.utils.notifications.parametrers

import android.graphics.Bitmap
import androidx.core.app.NotificationCompat

sealed class NotificatinStyles{
    abstract val style : NotificationCompat.Style

    data class BigTextStyle(
        val text : String,
        val bigTitle : String? = null,
        val summary : String? = null,
    ): NotificatinStyles(){
        override val style: NotificationCompat.Style
            get()  {
                val style = NotificationCompat.BigTextStyle().bigText(text)
                if(bigTitle != null)style.setBigContentTitle(bigTitle)
                if(summary != null)style.setSummaryText(summary)
                return style
            }

    }

    data class BigPictureStyle(
        val bitMap: Bitmap?,
        val largeIcon: Bitmap? = null,
        val bigTitle: String? = null,
        val summary: String? = null,
    ) : NotificatinStyles() {
        override val style: NotificationCompat.Style
            get() {
                val style = NotificationCompat.BigPictureStyle().bigPicture(bitMap)
                if (largeIcon != null) style.bigLargeIcon(largeIcon)
                if (bigTitle != null) style.setBigContentTitle(bigTitle)
                if (summary != null) style.setSummaryText(summary)
                return style
            }
    }

    data class InboxStyle(
        val lines: List<String>,
        val summary: String? = null,
    ) : NotificatinStyles() {
        override val style: NotificationCompat.Style
            get() {
                val style = NotificationCompat.InboxStyle()
                if (summary != null) style.setSummaryText(summary)
                lines.forEach { line -> style.addLine(line) }
                return style
            }
    }

    data class Other(override val style: NotificationCompat.Style) : NotificatinStyles()
}