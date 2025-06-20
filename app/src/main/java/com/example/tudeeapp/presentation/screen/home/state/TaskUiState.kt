package com.example.tudeeapp.presentation.screen.home.state

import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus


data class TaskUiState(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val priorityResIcon: Int,
    val status: TaskStatus,
    val categoryIcon: String = "R.drawable.ic_loading" ,
    val isCategoryPredefined: Boolean
)


