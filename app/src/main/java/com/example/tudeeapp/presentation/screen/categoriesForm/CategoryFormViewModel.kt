
package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.tudeeapp.domain.models.Category


class CategoryFormViewModel(val taskServices: TaskServices, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow(CategoryFormState())
    val state: StateFlow<CategoryFormState> = _state.asStateFlow()

    fun updateCategoryName(newCategoryName: String) {
        _state.update { it.copy(categoryName = newCategoryName) }
    }

    fun updateImage(uri: Uri) {
        _state.update { it.copy(imageUri = uri) }
    }

    fun addCategory() {
        viewModelScope.launch {
            try {
                val category = Category(
                    title=  state.value.categoryName,
                    imageUrl = state.value.imageUri.toString(),
                    tasksCount = 0,
                    isPredefined = false
                )
                taskServices.addCategory(category)
                _state.update { it.copy(successMessage = "Category added successfully") }
            }
            catch (e: Exception){
                _state.update {
                    it.copy(errorMessage = e.message ?: "Unexpected error")
                }
            }

        }
    }

}
