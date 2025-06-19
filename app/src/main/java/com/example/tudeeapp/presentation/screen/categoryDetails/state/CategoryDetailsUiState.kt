package com.example.tudeeapp.presentation.screen.categoryDetails.state

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task

data class CategoryDetailsUiState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val taskUiState: List<TaskUiState> = emptyList(),
    val categoryUiState: CategoryUiState? = null
)

data class TaskUiState(
    val id: Long,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val createdDate: String,
    val categoryId: Long
)

data class CategoryUiState(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val isPredefined: Boolean
)

fun Task.toTaskUiState(): TaskUiState = TaskUiState(
    id = id,
    title = title,
    description = description,
    priority = priority.name,
    status = status.name,
    createdDate = createdDate.toString(),
    categoryId = categoryId
)

fun Category.toCategoryUiState(): CategoryUiState = CategoryUiState(
    id = id,
    title = title,
    imageUrl = imageUrl,
    isPredefined = isPredefined
)