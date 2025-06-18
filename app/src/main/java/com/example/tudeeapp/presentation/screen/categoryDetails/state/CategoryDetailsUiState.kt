package com.example.tudeeapp.presentation.screen.categoryDetails.state

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task

data class CategoryDetailsUiState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val tasks: List<Task> = emptyList(),
    val category: Category? = null
)