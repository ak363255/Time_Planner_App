package com.example.utils.notifications.parametrers

import android.app.NotificationManager

enum class NotificationImportance(val importance: Int) {
    UNSPECIFIED(NotificationManager.IMPORTANCE_UNSPECIFIED),
    NONE(NotificationManager.IMPORTANCE_NONE),
    MIN(NotificationManager.IMPORTANCE_MIN),
    LOW(NotificationManager.IMPORTANCE_LOW),
    DEFAULT(NotificationManager.IMPORTANCE_DEFAULT),
    HIGH(NotificationManager.IMPORTANCE_HIGH),
    MAX(NotificationManager.IMPORTANCE_MAX)
}