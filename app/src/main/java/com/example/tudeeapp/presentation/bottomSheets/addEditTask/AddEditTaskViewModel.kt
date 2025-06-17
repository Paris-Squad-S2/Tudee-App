package com.example.tudeeapp.presentation.bottomSheets.addEditTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.TaskPriority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AddEditTaskViewModel(private val taskServices: TaskServices) : ViewModel() {
    private val _state = MutableStateFlow(
        AddEditTaskUiState(
            selectedPriority = TaskPriority.LOW,
            categories = emptyList()
        )
    )
    val state = _state.asStateFlow()


    init {
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            taskServices.getAllCategories().collect { categories ->
                _state.update {
                    it.copy(categories = categories.map { it.toCategoryState() })
                }
            }
        }
    }
}