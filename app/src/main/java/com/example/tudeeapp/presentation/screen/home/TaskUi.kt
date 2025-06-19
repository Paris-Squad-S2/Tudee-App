package com.example.tudeeapp.presentation.screen.home

import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus


data class TaskUi(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val priorityResIcon: Int,
    val status: TaskStatus,
    val categoryIcon: Int=R.drawable.ic_launcher_background
)
