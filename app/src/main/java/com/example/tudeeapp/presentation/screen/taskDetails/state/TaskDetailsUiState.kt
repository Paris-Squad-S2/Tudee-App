package com.example.tudeeapp.presentation.screen.taskDetails.state

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.datetime.LocalDate

data class TaskDetailsUiState(
    val taskUiState: TaskUiState? = null,
    val categoryUiState: CategoryUiState? = null,
    val isLoading: Boolean = true,
    val errorMessage: String = ""
)

data class TaskUiState(
    val id: Long = -1,
    val title: String,
    val description: String,
    val priority: TaskPriority,
    val status: TaskStatus,
    val createdDate: LocalDate,
    val categoryId: Long,
)

data class CategoryUiState(
    val imageUri: String,
    val isPredefined: Boolean
)

fun Task.toTaskUiState(): TaskUiState {
    return TaskUiState(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority,
        status = this.status,
        createdDate = this.createdDate,
        categoryId = this.categoryId
    )
}

fun Category.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        imageUri = this.imageUri,
        isPredefined = isPredefined
    )
}