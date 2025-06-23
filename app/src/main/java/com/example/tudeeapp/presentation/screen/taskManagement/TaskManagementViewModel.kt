package com.example.tudeeapp.presentation.screen.taskManagement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.random.Random

class TaskManagementViewModel(
    private val taskServices: TaskServices,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskManagementUiState>(TaskManagementUiState(isLoading = true)) {

    val taskId = savedStateHandle.toRoute<Destinations.TaskManagement>().taskId

    init {
        _uiState.update { it.copy(isEditMode = taskId != null) }
        getAllCategories()
    }

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onDateClicked(isVisible: Boolean) {
        _uiState.update { it.copy(isDatePickerVisible = isVisible) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date.toString(), isDatePickerVisible = false) }
    }

    fun onPrioritySelected(priorityUiState: TaskPriorityUiState) {
        _uiState.update { currentState ->
            currentState.copy(selectedPriority = priorityUiState)
        }
    }

    fun popBackStack() {
        navigateUp()
    }

    fun onCategorySelected(categoryId: Long) {
        _uiState.update { currentState ->
            val isCurrentlySelected = currentState.selectedCategoryId == categoryId
            val updatedCategories = currentState.categories.map { category ->
                category.copy(isSelected = !isCurrentlySelected && category.id == categoryId)
            }
            currentState.copy(
                categories = updatedCategories,
                selectedCategoryId = if (isCurrentlySelected) null else categoryId
            )
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            try {
                taskServices.getAllCategories().collect { categories ->
                    _uiState.update {
                        it.copy(
                            categories = categories.map { category -> category.toCategoryState() },
                            isLoading = false
                        )
                    }
                }
            } catch (_: Exception) {
                handleException()
            }
        }
        taskId?.let { getTaskById(it) }
    }

    fun onActionButtonClicked() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val currentState = _uiState.value

                val task = Task(
                    id = taskId ?: Random.nextLong(1L, Long.MAX_VALUE),
                    title = currentState.title,
                    description = currentState.description,
                    priority = currentState.selectedPriority.toTaskPriority() ?: TaskPriority.LOW,
                    status = if (currentState.isEditMode) currentState.taskStatus else TaskStatus.TO_DO,
                    createdDate = LocalDate.parse(currentState.selectedDate),
                    categoryId = currentState.selectedCategoryId ?: 0L
                )

                if (currentState.isEditMode) taskServices.editTask(task) else taskServices.addTask(
                    task
                )
                _uiState.update { it.copy(isLoading = false, isTaskSaved = true) }

            } catch (_: Exception) {
                handleException()
            }
        }
    }

    private fun getTaskById(id: Long) {
        viewModelScope.launch {
            try {
                taskServices.getTaskById(id).collect { task ->
                    _uiState.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            selectedDate = task.createdDate.toString(),
                            selectedPriority = TaskPriorityUiState.fromDomain(task.priority),
                            selectedCategoryId = task.categoryId,
                            taskStatus = task.status,
                            isLoading = false,
                            categories = it.categories.map { category ->
                                category.copy(isSelected = category.id == task.categoryId)
                            })
                    }
                }
            } catch (_: Exception) {
                handleException()
            }
        }
    }


    private fun handleException() {
        _uiState.update {
            it.copy(
                isLoading = false,
                error = "There was an error processing your request. Please try again later."
            )
        }
    }
}