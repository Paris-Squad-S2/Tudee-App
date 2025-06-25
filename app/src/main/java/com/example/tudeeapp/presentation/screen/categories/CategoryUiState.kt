package com.example.tudeeapp.presentation.screen.categories

import com.example.tudeeapp.domain.models.Category

data class CategoriesScreenState(
    val categories: List<CategoryUIState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategoryUIState(
    val id: Long,
    val name: String,
    val count: Int,
    val imageUri: String,
    val isPredefined: Boolean
)

fun Category.toCategoryUIState(calculatedCount: Int): CategoryUIState {
    return CategoryUIState(
        id = this.id,
        name = this.title,
        count = calculatedCount,
        imageUri = this.imageUri,
        isPredefined = this.isPredefined
    )
}