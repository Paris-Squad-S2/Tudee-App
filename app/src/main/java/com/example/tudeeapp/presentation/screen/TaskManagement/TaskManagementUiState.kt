package com.example.tudeeapp.presentation.screen.TaskManagement

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.TaskPriority

data class TaskManagementUiState(
    val isDatePickerVisible: Boolean = false,
    val title: String = "",
    val description: String = "",
    val selectedDate: String = "",
    val selectedPriority: TaskPriorityUiState = TaskPriorityUiState.NONE,
    val categories: List<CategoryUiState> = emptyList<CategoryUiState>(),
    val selectedCategoryId: Int? = null,
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val error: String? = null,
) {
    val isInitialState: Boolean
        get() = title.isEmpty() ||
                description.isEmpty() ||
                selectedDate.isEmpty() ||
                selectedPriority == TaskPriorityUiState.NONE ||
                categories.isEmpty() ||
                selectedCategoryId == null
}

data class CategoryUiState(
    val title: String = "",
    val isSelected: Boolean = false,
    val image: String,
    val isPredefined: Boolean = false,
)

fun Category.toCategoryState() = CategoryUiState(
    title = this.title,
    isSelected = false,
    image = this.imageUrl,
    isPredefined = this.isPredefined
)

enum class TaskPriorityUiState {
    NONE,
    LOW,
    MEDIUM,
    HIGH;

    companion object {
        fun fromDomain(priority: TaskPriority?): TaskPriorityUiState = when (priority) {
            TaskPriority.LOW -> LOW
            TaskPriority.MEDIUM -> MEDIUM
            TaskPriority.HIGH -> HIGH
            null -> NONE
        }
    }
}

fun TaskPriorityUiState.toDomain(): TaskPriority? {
    return when (this) {
        TaskPriorityUiState.LOW -> TaskPriority.LOW
        TaskPriorityUiState.MEDIUM -> TaskPriority.MEDIUM
        TaskPriorityUiState.HIGH -> TaskPriority.HIGH
        TaskPriorityUiState.NONE -> null
    }
}