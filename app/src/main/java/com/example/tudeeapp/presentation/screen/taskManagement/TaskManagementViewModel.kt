package com.example.tudeeapp.presentation.screen.taskManagement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.random.Random

class TaskManagementViewModel(
    private val taskServices: TaskServices,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val taskId = savedStateHandle.toRoute<Screens.TaskManagement>().taskId

    private val _state =
        MutableStateFlow(TaskManagementUiState(isEditMode = taskId != null, isLoading = true))

    val state = _state.asStateFlow()



    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            try {
                taskServices.getAllCategories().collect { categories ->
                    _state.update {
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

    fun onTitleChange(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun onDescriptionChange(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onDateClicked(isVisible: Boolean) {
        _state.update { it.copy(isDatePickerVisible = isVisible) }
    }

    fun onDateSelected(date: LocalDate) {
        _state.update { it.copy(selectedDate = date.toString(), isDatePickerVisible = false) }
    }

    fun onPrioritySelected(priorityUiState: TaskPriorityUiState) {
        _state.update { currentState ->
            currentState.copy(selectedPriority = priorityUiState)
        }
    }

    fun onCategorySelected(categoryId: Long) {
        _state.update { currentState ->
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

    fun onActionButtonClicked() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                val currentState = _state.value

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
                _state.update { it.copy(isLoading = false, isTaskSaved = true) }

            } catch (_: Exception) {
                handleException()
            }
        }
    }

    private fun getTaskById(id: Long) {
        viewModelScope.launch {
            try {
                taskServices.getTaskById(id).collect { task ->
                    _state.update {
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
        _state.update {
            it.copy(
                isLoading = false,
                error = "There was an error processing your request. Please try again later."
            )
        }
    }
}