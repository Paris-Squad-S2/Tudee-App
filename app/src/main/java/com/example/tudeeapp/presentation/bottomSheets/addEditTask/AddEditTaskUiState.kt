package com.example.tudeeapp.presentation.bottomSheets.addEditTask

import androidx.annotation.DrawableRes
import com.example.tudeeapp.domain.models.TaskPriority

data class AddEditTaskUiState(
    val title: String = "",
    val description: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val selectedPriority: TaskPriority,
    val categories : List<Category>,
    val selectedCategoryId: Int = -1,
    val isLoading: Boolean = false
)

data class Category(
    val title: String="",
    val isSelected: Boolean = false,
    @DrawableRes val image : Int = -1
)