package com.example.tudeeapp.presentation.screen.categories

import com.example.tudeeapp.data.mapper.DataConstant.toResDrawables
import com.example.tudeeapp.domain.models.Category

fun Category.toCategoryUIState(): CategoryItemUIState {
    return CategoryItemUIState(
        name = this.title,
        imageResId = this.imageUrl.toResDrawables(),
        count = this.tasksCount,
        id = this.id
    )
}