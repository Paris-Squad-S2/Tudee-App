package com.example.tudeeapp.presentation.screen.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(private val taskServices: TaskServices) : ViewModel() {
    private val _state = MutableStateFlow(CategoryUIState())
    val state = _state.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                taskServices.getAllTasks().combine(taskServices.getAllCategories()) {tasks, categories ->
                    val taskCounts = tasks
                        .groupBy { it.categoryId }
                        .mapValues { it.value.size }

                    val updatedCategories = categories.map { category ->
                        val count = taskCounts[category.id] ?: 0
                        category.toCategoryUIState(count)
                    }
                    _state.value.copy(
                        isLoading = false,
                        categories = updatedCategories
                    )
                }.collect { updatedUiState ->
                    _state.value = updatedUiState
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}