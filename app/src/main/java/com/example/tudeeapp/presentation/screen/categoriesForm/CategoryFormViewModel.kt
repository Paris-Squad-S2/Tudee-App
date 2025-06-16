package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CategoryFormViewModel : ViewModel() {

    private val _state = MutableStateFlow(CategoryFormState())
    val state: StateFlow<CategoryFormState> = _state

    fun updateCategoryName(newCategoryName: String) {
        _state.update { it.copy(categoryName = newCategoryName) }
    }

    fun updateImage(uri: Uri) {
        _state.update { it.copy(imageUri = uri) }
    }


    fun dismissForm() {
        _state.update { it.copy(isVisible = false) }
    }


    fun submitCategory() {

    }
}