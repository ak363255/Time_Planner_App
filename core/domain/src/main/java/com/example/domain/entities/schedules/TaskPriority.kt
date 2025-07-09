package com.example.domain.entities.schedules

enum class TaskPriority {
    STANDARD,MEDIUM,MAX;
    fun isImportant() = this != STANDARD
}