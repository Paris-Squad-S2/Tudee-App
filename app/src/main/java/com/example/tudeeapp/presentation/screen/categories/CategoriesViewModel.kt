package com.example.tudeeapp.presentation.screen.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.NoCategoriesFoundException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
                taskServices.getAllCategories().collect { categories ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            categories = categories.map { it.toCategoryUIState() }
                        )
                    }
                }
            } catch (e: NoCategoriesFoundException) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}