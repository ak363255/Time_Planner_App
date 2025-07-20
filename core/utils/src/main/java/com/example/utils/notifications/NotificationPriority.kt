package com.example.utils.notifications

import android.app.NotificationManager

enum class NotificationPriority(val importance: Int) {
    DEFAULT(NotificationManager.IMPORTANCE_DEFAULT),
    MIN(NotificationManager.IMPORTANCE_MIN),
    LOW(NotificationManager.IMPORTANCE_LOW),
    HIGH(NotificationManager.IMPORTANCE_HIGH),
    MAX(NotificationManager.IMPORTANCE_MAX),
}