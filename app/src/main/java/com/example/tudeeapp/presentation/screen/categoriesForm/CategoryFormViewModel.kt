package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.presentation.mapper.toResDrawables
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryFormViewModel(
    val taskServices: TaskServices,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryFormState())
    val state: StateFlow<CategoryFormState> = _state.asStateFlow()

    init {
        val args = savedStateHandle.get<Long>("categoryId")
        Log.d("EditCategory", "Received categoryId: $args")
        if (args != null) {
            loadCategory(args)
        }
    }

    private fun loadCategory(id: Long) {
        viewModelScope.launch {
            val category = taskServices.getCategoryById(id).first()
            _state.update {
                it.copy(
                    categoryName = category.title,
                    categoryId = category.id,
                    imageUri = if (category.imageUrl.startsWith("R.drawable.")) {
                        val resourceId = category.imageUrl.toResDrawables()
                        "android.resource://com.example.tudeeapp/$resourceId".toUri()
                    } else {
                        category.imageUrl.toUri()
                    }
                )
            }
        }
    }

    fun updateCategoryName(newName: String) {
        _state.update { it.copy(categoryName = newName) }
    }

    fun updateImage(uri: Uri) {
        _state.update { it.copy(imageUri = uri) }
    }

    fun submitCategory() {
        if (state.value.categoryId != 0L) {
            editCategory()
        } else {
            addCategory()
        }
    }

    fun editCategory() {
        viewModelScope.launch {
            try {
                taskServices.editCategory(
                    id = state.value.categoryId,
                    title = state.value.categoryName,
                    imageUrl = getImageUrl(state.value.imageUri.toString())
                )
                _state.update { it.copy(successMessage = "Category updated successfully") }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Failed to edit category") }
            }
        }
    }

    private fun getImageUrl(url: String): String {
        if (url.startsWith("content")) {
            return url
        } else {
            return url.getLastPartAfterSlash()
        }
    }

    private fun addCategory() {
        viewModelScope.launch {
            try {
                val category = Category(
                    title = state.value.categoryName,
                    imageUrl = state.value.imageUri.toString(),
                    tasksCount = 0,
                    isPredefined = false
                )
                taskServices.addCategory(category)
                _state.update { it.copy(successMessage = "Category added successfully") }
            } catch (e: Exception) {
                _state.update {
                    it.copy(errorMessage = e.message ?: "Unexpected error")
                }
            }
        }
    }


}

fun String.getLastPartAfterSlash(): String {
    return this.split("/").last()
}

