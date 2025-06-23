package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.presentation.mapper.toResDrawables
import com.example.tudeeapp.presentation.navigation.Destinations
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

    private val _state = MutableStateFlow(CategoryFormUIState())
    val state: StateFlow<CategoryFormUIState> = _state.asStateFlow()

    init {
        savedStateHandle.toRoute<Destinations.CategoryForm>().categoryId?.let {
            loadCategory(it)
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
                _state.update { it.copy(successMessage = R.string.edited_category_successfully) }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = R.string.failed_to_edit_category) }
            }
        }
    }

    fun deleteCategory(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                taskServices.deleteCategory(state.value.categoryId)
                onSuccess()
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = R.string.failed_to_delete_category) }
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
                _state.update { it.copy(successMessage = R.string.category_added_successfully) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(errorMessage = R.string.failed_to_add_category)
                }
            }
        }
    }


}

fun String.getLastPartAfterSlash(): String {
    return this.split("/").last()
}

