package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.categoryDetails.state.CategoryDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskServices: TaskServices,
) : ViewModel() {

    private val categoryId: Long = savedStateHandle.toRoute<Screens.CategoryDetails>().categoryId

    private val _category = MutableStateFlow(CategoryDetailsUiState())
    val category = _category.asStateFlow()


    init {
        loadCategory(categoryId)
    }

    private val _stateFilter = MutableStateFlow(TaskStatus.IN_PROGRESS)
    val stateFilter = _stateFilter.asStateFlow()

    private fun loadCategory(categoryId: Long){
        viewModelScope.launch {
            _category.value = _category.value.copy(isLoading = true, errorMessage = "")
            try {
                val category = taskServices.getCategoryById(categoryId)
                taskServices.getAllTasks().collect { tasks ->
                    val filtered = tasks.filter { it.categoryId == categoryId }
                    _category.value = CategoryDetailsUiState(
                        isLoading = false,
                        tasks = filtered,
                        category = category
                    )
                }
            } catch (e: Exception) {
                _category.value = CategoryDetailsUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun setStatus(status: TaskStatus) {
        _stateFilter.value = status
    }
}
