package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskServices,
) : ViewModel() {

    private val categoryId: Long = savedStateHandle.toRoute<Destinations.CategoryDetails>().categoryId

    private val _uiState = MutableStateFlow(CategoryDetailsUiState())
    val uiState: StateFlow<CategoryDetailsUiState> = _uiState.asStateFlow()


    init {
        loadCategory(categoryId)
    }

    private val _stateFilter = MutableStateFlow(TaskStatus.IN_PROGRESS)
    val stateFilter = _stateFilter.asStateFlow()

    private fun loadCategory(categoryId: Long) {
        viewModelScope.launch {
            try {
                val category = taskService.getCategoryById(categoryId)
                val tasks = taskService.getAllTasks().first()
                    .filter { it.categoryId == categoryId }

                _uiState.value = CategoryDetailsUiState(
                    isLoading = false,
                    taskUiState = tasks.map { it.toTaskUiState() },
                    categoryUiState = category.first().toCategoryUiState()
                )

            } catch (e: Exception) {
                _uiState.value = CategoryDetailsUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "Unexpected Error"
                )
            }
        }
    }
    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")

            try {
                taskService.deleteTask(taskId)
                updateUiStateWithFilters()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun updateUiStateWithFilters() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")

                val category = taskService.getCategoryById(categoryId).first()
                val allTasks = taskService.getAllTasks().first()
                val filteredTasks = allTasks.filter { it.categoryId == categoryId }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    categoryUiState = category.toCategoryUiState(),
                    taskUiState = filteredTasks.map { it.toTaskUiState() }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error updating tasks"
                )
            }
        }
    }

    fun setStatus(status: TaskStatus) {
        _stateFilter.value = status
    }

    fun refreshCategory() {
        loadCategory(categoryId)
    }

}
