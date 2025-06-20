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
)

fun Category.toCategoryUIState(): CategoryItemUIState {
    val isPredefined = this.isPredefined
    return CategoryItemUIState(
        id = this.id,
        name = this.title,
        count = this.tasksCount,
        imageResId = if (this.isPredefined) this.imageUrl.toResDrawables() else null,
        imageUrl = if (!this.isPredefined) this.imageUrl else null
    )
}