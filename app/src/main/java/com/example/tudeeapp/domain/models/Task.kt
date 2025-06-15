package com.example.tudeeapp.domain.models

import kotlinx.datetime.LocalDate

data class Task(
    val id: Long=0L,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdDate: LocalDate,
    val categoryId: Long=0L
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
