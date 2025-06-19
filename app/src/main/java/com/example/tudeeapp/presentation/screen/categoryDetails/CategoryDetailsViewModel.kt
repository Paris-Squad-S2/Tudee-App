package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.categoryDetails.state.CategoryDetailsUiState
import com.example.tudeeapp.presentation.screen.categoryDetails.state.TaskUiState
import com.example.tudeeapp.presentation.screen.categoryDetails.state.toCategoryUiState
import com.example.tudeeapp.presentation.screen.categoryDetails.state.toTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskServices,
) : ViewModel() {

    private val categoryId: Long = savedStateHandle.toRoute<Screens.CategoryDetails>().categoryId

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

    fun setStatus(status: TaskStatus) {
        _stateFilter.value = status
    }
}
