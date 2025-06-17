package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class CategoryDetailsViewModel(
    private val taskServices: TaskServices,
) : ViewModel() {

    private val _allTasksState = MutableStateFlow<CategoryDetailsUiState>(CategoryDetailsUiState.Loading)
    val allTasksState = _allTasksState.asStateFlow()

    private val _stateFilter = MutableStateFlow(TaskStatus.IN_PROGRESS)
    val stateFilter = _stateFilter.asStateFlow()

    fun loadTasks(categoryId: Long) {
        viewModelScope.launch {
            _allTasksState.value = CategoryDetailsUiState.Loading
            try {
                taskServices.getAllTasks().collect { tasks ->
                    val filtered = tasks.filter { it.categoryId == categoryId }
                    _allTasksState.value = CategoryDetailsUiState.Success(filtered)
                }
            } catch (e: Exception) {
                _allTasksState.value = CategoryDetailsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setStatus(status: TaskStatus) {
        _stateFilter.value = status
    }
}
