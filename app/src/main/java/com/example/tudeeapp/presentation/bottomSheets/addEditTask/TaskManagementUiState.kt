package com.example.tudeeapp.presentation.bottomSheets.addEditTask

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.TaskPriority

data class TaskManagementUiState(
    val isDatePickerVisible: Boolean = false,
    val title: String = "",
    val description: String = "",
    val selectedDate: String = "today",
    val selectedPriority: TaskPriorityUiState = TaskPriorityUiState.None,
    val categories: List<CategoryUiState> = emptyList<CategoryUiState>(),
    val selectedCategoryId: Int? = null,
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val error: String? = null
){
    val isInitialState: Boolean
        get() = title.isEmpty() ||
                description.isEmpty() ||
                selectedDate.isEmpty() ||
                selectedPriority is TaskPriorityUiState.None ||
                categories.isEmpty() ||
                selectedCategoryId == null
}

data class CategoryUiState(
    val title: String = "",
    val isSelected: Boolean = false,
    val image: Int = com.example.tudeeapp.R.drawable.ic_unselected_categories,
)

fun Category.toCategoryState() = CategoryUiState(
    title = this.title,
    isSelected = false,
    //  image = this.imageUrl.toDrawableResourceId()
)

sealed class TaskPriorityUiState {
    object None : TaskPriorityUiState()
    data class Selected(val priority: TaskPriority) : TaskPriorityUiState()
}