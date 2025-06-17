package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryFormViewModel(val taskServices: TaskServices, category: Category) : ViewModel() {

    private val _state = MutableStateFlow(
        CategoryFormState(
            categoryName = category.title,
            categoryId = category.id,
            imageUri = category.imageUrl.toUri()
        )
    )
    val state: StateFlow<CategoryFormState> = _state.asStateFlow()

    fun updateCategoryName(newCategoryName: String) {
        _state.update { it.copy(categoryName = newCategoryName) }
    }

    fun updateImage(uri: Uri) {
        _state.update { it.copy(imageUri = uri) }
    }

    fun addCategory(title: String, imageUrl: String) {
        viewModelScope.launch {
            taskServices.addCategory(title = title, imageUrl = imageUrl)
        }
    }

    fun editCategory() {
        viewModelScope.launch {
            taskServices.editCategory(
                id = state.value.categoryId,
                title = state.value.categoryName,
                imageUrl = state.value.imageUri.toString()
            )
        }
    }

    fun dismissForm() {
        _state.update { it.copy(isVisible = false) }
    }

    fun submitCategory() {

    }
}