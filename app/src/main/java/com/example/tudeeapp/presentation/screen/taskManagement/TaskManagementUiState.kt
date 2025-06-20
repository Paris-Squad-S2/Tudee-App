package com.example.tudeeapp.presentation.screen.taskManagement

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.extentions.getCurrentDateString

data class TaskManagementUiState(
    val isDatePickerVisible: Boolean = false,
    val title: String = "",
    val description: String = "",
    val selectedDate: String = getCurrentDateString(),
    val selectedPriority: TaskPriorityUiState = TaskPriorityUiState.NONE,
    val categories: List<CategoryUiState> = emptyList<CategoryUiState>(),
    val selectedCategoryId: Long? = null,
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val error: String? = null,
    val taskStatus: TaskStatus = TaskStatus.TO_DO,
    val isTaskSaved: Boolean = false,
) {
    val isInitialState: Boolean
        get() = title.isEmpty() ||
                selectedDate.isEmpty() ||
                selectedPriority == TaskPriorityUiState.NONE ||
                categories.isEmpty() ||
                selectedCategoryId == null
}

data class CategoryUiState(
    val id : Long = 0,
    val title: String = "",
    val isSelected: Boolean = false,
    val image: String,
    val isPredefined: Boolean = false,
)

fun Category.toCategoryState() = CategoryUiState(
    id = this.id,
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

fun TaskPriorityUiState.toTaskPriority(): TaskPriority? {
    return when (this) {
        TaskPriorityUiState.LOW -> TaskPriority.LOW
        TaskPriorityUiState.MEDIUM -> TaskPriority.MEDIUM
        TaskPriorityUiState.HIGH -> TaskPriority.HIGH
        TaskPriorityUiState.NONE -> null
    }
}