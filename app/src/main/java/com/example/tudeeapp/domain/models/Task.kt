package com.example.tudeeapp.domain.models

import kotlinx.datetime.LocalDate

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdDate: LocalDate,
    val categoryId: Long
)
