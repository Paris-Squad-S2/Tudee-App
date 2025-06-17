package com.example.tudeeapp.presentation.bottomSheets.addEditTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TaskManagementViewModel(private val taskServices: TaskServices) : ViewModel() {
    private val _state = MutableStateFlow(
        TaskManagementUiState(selectedPriority = TaskPriorityUiState.None, categories = emptyList())
    )
    val state = _state.asStateFlow()

    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            taskServices.getAllCategories().collect { categories ->
                _state.update {
                    it.copy(categories = categories.map { category -> category.toCategoryState() })
                }
            }
        }
    }

    fun onTitleChange(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun onDescriptionChange(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onDateClicked(isVisible: Boolean) {
        _state.update { it.copy(isDatePickerVisible = isVisible) }
    }

    fun onPrioritySelected(priority: TaskPriority) {
        _state.update { currentState ->
            val isCurrentlySelected =
                currentState.selectedPriority is TaskPriorityUiState.Selected &&
                        currentState.selectedPriority.priority == priority
            currentState.copy(
                selectedPriority = if (isCurrentlySelected) TaskPriorityUiState.None else TaskPriorityUiState.Selected(
                    priority
                )
            )
        }
    }

    fun onCategorySelected(categoryId: Int) {
        _state.update { currentState ->
            val isCurrentlySelected = currentState.selectedCategoryId == categoryId
            val updatedCategories = currentState.categories.mapIndexed { index, category ->
                category.copy(isSelected = if (isCurrentlySelected) false else index == categoryId)
            }
            currentState.copy(
                categories = updatedCategories,
                selectedCategoryId = if (isCurrentlySelected) null else categoryId
            )
        }
    }

    fun onActionButtonClicked() {
        viewModelScope.launch {
            try {
                val currentState = _state.value

                val createDate: LocalDate = LocalDate.parse(currentState.selectedDate)

                taskServices.addTask(
                    Task(
                        title = currentState.title,
                        description = currentState.description,
                        priority = currentState.selectedPriority.toDomain(),
                        status = TaskStatus.TO_DO,
                        createdDate = createDate,
                        categoryId = currentState.selectedCategoryId?.toLong()
                            ?: throw IllegalArgumentException("Selected category ID is null")
                    )
                )
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "An error occurred") }
            }
        }
    }

    fun onCancelClicked() {
        _state.update { it.copy(isSheetVisible = false) }
    }
}