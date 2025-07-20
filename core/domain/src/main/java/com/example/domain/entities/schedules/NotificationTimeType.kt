package com.example.domain.entities.schedules

enum class NotificationTimeType{
    BEFORE_TASK,START_TASK,AFTER_TASK
}
fun TaskNotificationType.toTimeType() = when (this) {
    TaskNotificationType.START ->  NotificationTimeType.START_TASK
    TaskNotificationType.FIFTEEN_MINUTES_BEFORE ->  NotificationTimeType.BEFORE_TASK
    TaskNotificationType.ONE_HOUR_BEFORE ->  NotificationTimeType.BEFORE_TASK
    TaskNotificationType.THREE_HOUR_BEFORE ->  NotificationTimeType.BEFORE_TASK
    TaskNotificationType.ONE_DAY_BEFORE ->  NotificationTimeType.BEFORE_TASK
    TaskNotificationType.ONE_WEEK_BEFORE ->  NotificationTimeType.BEFORE_TASK
    TaskNotificationType.AFTER_START_BEFORE_END ->  NotificationTimeType.AFTER_TASK
}
