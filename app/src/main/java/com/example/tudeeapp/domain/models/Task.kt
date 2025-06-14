package com.example.tudeeapp.domain.models

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdDate: LocalDate, // add kotlinx dependency in the gradle ->implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
    val category: TaskCategory
)
