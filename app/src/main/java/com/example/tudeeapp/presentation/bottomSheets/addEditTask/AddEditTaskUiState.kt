package com.example.tudeeapp.presentation.bottomSheets.addEditTask


import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.TaskPriority

data class AddEditTaskUiState(
    val title: String = "",
    val description: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val selectedPriority: TaskPriority = TaskPriority.LOW,
    val categories: List<CategoryState> = emptyList<CategoryState>(),
    val selectedCategoryId: Int = -1,
    val isLoading: Boolean = false,
)

data class CategoryState(
    val title: String = "",
    val isSelected: Boolean = false,
    val image: Int = -1,
)

fun Category.toCategoryState() = CategoryState(
    title = this.title,
    isSelected = false,
    image = this.imageUrl.toInt()
)