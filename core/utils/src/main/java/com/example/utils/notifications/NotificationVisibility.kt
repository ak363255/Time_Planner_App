package com.example.utils.notifications

import androidx.core.app.NotificationCompat

enum class NotificationVisibility(val visibility: Int) {
    PUBLIC(NotificationCompat.VISIBILITY_PUBLIC),
    SECRET(NotificationCompat.VISIBILITY_SECRET),
    PRIVATE(NotificationCompat.VISIBILITY_PRIVATE),
}