package com.example.tudeeapp.presentation.screen.categories

import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class CategoriesViewModel(private val taskServices: TaskServices) :
    BaseViewModel<CategoriesScreenState>(CategoriesScreenState()) {

    init {
        getCategories()
    }

    private fun getCategories() {
        launchSafely(
            onLoading = { _uiState.update { it.copy(isLoading = true) } },
            onSuccess = {
                taskServices.getAllTasks()
                    .combine(taskServices.getAllCategories()) { tasks, categories ->
                        val taskCounts = tasks
                            .groupBy { it.categoryId }
                            .mapValues { it.value.size }

                        val updatedCategories = categories.map { category ->
                            val count = taskCounts[category.id] ?: 0
                            category.toCategoryUIState(count)
                        }
                        _uiState.value.copy(
                            isLoading = false,
                            categories = updatedCategories
                        )
                    }.collect { updatedUiState ->
                        _uiState.value = updatedUiState
                    }
            },
            onError = { errorMessage ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        )
    }
}