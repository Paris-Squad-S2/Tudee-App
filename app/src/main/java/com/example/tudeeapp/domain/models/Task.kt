package com.example.tudeeapp.domain.models

import kotlinx.datetime.LocalDate
import kotlin.random.Random

data class Task(
    val id: Long = Random.nextLong(1L, Long.MAX_VALUE),
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdDate: LocalDate,
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
