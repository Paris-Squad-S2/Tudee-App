package com.example.tudeeapp.presentation.screen.categories

import com.example.tudeeapp.domain.models.Category

data class CategoryUIState(
    val categories: List<CategoryItemUIState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategoryItemUIState(
    val id: Long,
    val name: String,
    val count: Int,
    val imageUri: String,
    val isPredefined: Boolean
)

fun Category.toCategoryUIState(calculatedCount: Int): CategoryItemUIState {
    return CategoryItemUIState(
        id = this.id,
        name = this.title,
        count = calculatedCount,
        imageUri = this.imageUrl,
        isPredefined = this.isPredefined
    )
}