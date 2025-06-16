package com.example.tudeeapp.presentation.screen.categories.viewModel.state

import androidx.annotation.DrawableRes

data class CategoryUIState(
    val categories: List<CategoryItemUIState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategoryItemUIState(
    val name: String,
    @DrawableRes val imageResId: Int,
    val count: Int
)