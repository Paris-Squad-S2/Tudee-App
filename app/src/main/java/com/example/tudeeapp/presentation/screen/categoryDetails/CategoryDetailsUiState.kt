package com.example.tudeeapp.presentation.screen.categoryDetails

import com.example.tudeeapp.domain.models.Task

sealed class CategoryDetailsUiState {
    object Loading : CategoryDetailsUiState()
    data class Success(val data: List<Task>) : CategoryDetailsUiState()
    data class Error(val message: String) : CategoryDetailsUiState()
}