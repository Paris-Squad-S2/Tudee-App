package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first


class CategoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskService: TaskServices,
) : BaseViewModel<CategoryDetailsUiState>(CategoryDetailsUiState()) {

    private val categoryId: Long = savedStateHandle.toRoute<Destinations.CategoryDetails>().categoryId

    init {
        loadCategory(categoryId)
    }

    private val _stateFilter = MutableStateFlow(TaskStatus.IN_PROGRESS)
    val stateFilter = _stateFilter.asStateFlow()

    private fun loadCategory(categoryId: Long) {
        launchSafely(
            onLoading = {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")
            },
            onSuccess = {
                val category = taskService.getCategoryById(categoryId)
                val tasks = taskService.getAllTasks().first()
                    .filter { it.categoryId == categoryId }

                _uiState.value = CategoryDetailsUiState(
                    isLoading = false,
                    taskUiState = tasks.map { it.toTaskUiState() },
                    categoryUiState = category.first().toCategoryUiState()
                )
            },
            onError = { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error
                )
            }
        )
    }

    fun deleteTask(taskId: Long) {
        launchSafely(
            onLoading = {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMessage = "Unknown error occurred"
                )
            },
            onSuccess = {
                taskService.deleteTask(taskId)
                updateUiStateWithFilters()
            },
            onError = { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = error
                )
            }
        )
    }

    fun updateUiStateWithFilters() {
        launchSafely(
            onLoading = {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")
            },
            onSuccess = {
                val category = taskService.getCategoryById(categoryId).first()
                val allTasks = taskService.getAllTasks().first()
                val filteredTasks = allTasks.filter { it.categoryId == categoryId }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    categoryUiState = category.toCategoryUiState(),
                    taskUiState = filteredTasks.map { it.toTaskUiState() }
                )
            },
            onError = {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = it
                )
            }
        )
    }

    fun setStatus(status: TaskStatus) {
        _stateFilter.value = status
    }

    fun refreshCategory() {
        loadCategory(categoryId)
    }

    fun onClickEditCategory() {
        uiState.value.categoryUiState?.let {
            navigate(Destinations.CategoryForm(it.id))
        }
    }

    fun onClickBack(){
        navigateUp()
    }
}
