package com.example.tudeeapp.presentation.screen.categories

import androidx.annotation.DrawableRes
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.presentation.mapper.toResDrawables

data class CategoryUIState(
    val categories: List<CategoryItemUIState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategoryItemUIState(
    val id: Long,
    val name: String,
    val count: Int,
    val imageUrl: String? = null,
    @DrawableRes val imageResId: Int? = null,
    val isPredefined: Boolean
)

fun Category.toCategoryUIState(calculatedCount: Int): CategoryItemUIState {
    return CategoryItemUIState(
        id = this.id,
        name = this.title,
        count = calculatedCount,
        imageResId = if (this.isPredefined) this.imageUrl.toResDrawables() else null,
        imageUrl = if (!this.isPredefined) this.imageUrl else null,
        isPredefined = this.isPredefined
    )
}