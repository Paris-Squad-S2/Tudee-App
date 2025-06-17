package com.example.tudeeapp.presentation.screen.categories

import androidx.annotation.DrawableRes
import com.example.tudeeapp.data.mapper.DataConstant.toResDrawables
import com.example.tudeeapp.domain.models.Category

data class CategoryUIState(
    val categories: List<CategoryItemUIState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class CategoryItemUIState(
    val id: Long,
    val name: String,
    @DrawableRes val imageResId: Int,
    val count: Int
)

fun Category.toCategoryUIState(): CategoryItemUIState {
    return CategoryItemUIState(
        name = this.title,
        imageResId = this.imageUrl.toResDrawables(),
        count = this.tasksCount,
        id = this.id
    )
}