package com.example.tudeeapp.domain.models

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Long =0,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdDate: LocalDateTime,
    val categoryId: Long,
)
enum class TaskPriority {
    HIGH,
    MEDIUM,
    LOW
}
enum class TaskStatus {
    TO_DO,
    IN_PROGRESS,
    DONE
}
