package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.presentation.mapper.toResDrawables
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import com.example.tudeeapp.presentation.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class CategoryFormViewModel(
    val taskServices: TaskServices,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CategoryFormUIState>(CategoryFormUIState()) {

    init {
        savedStateHandle.toRoute<Destinations.CategoryForm>().categoryId?.let {
            loadCategory(it)
        }
    }

    private fun loadCategory(id: Long) {
        launchSafely(
            onSuccess = {
                val category = taskServices.getCategoryById(id).first()
                _uiState.update {
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
            },
            onError = {
                _uiState.update {
                    it.copy(
                        errorMessage = R.string.failed_to_load_category
                    )
                }
            }
        )
    }

    fun updateCategoryName(newName: String) {
        _uiState.update { it.copy(categoryName = newName) }
    }

    fun updateImage(uri: Uri) {
        _uiState.update { it.copy(imageUri = uri) }
    }

    fun submitCategory() {
        if (_uiState.value.categoryId != 0L) {
            editCategory()
        } else {
            addCategory()
        }
    }

    fun editCategory() {
        launchSafely(
            onSuccess = {
                taskServices.editCategory(
                    id = _uiState.value.categoryId,
                    title = _uiState.value.categoryName,
                    imageUrl = getImageUrl(_uiState.value.imageUri.toString())
                )
                _uiState.update { it.copy(successMessage = R.string.edited_category_successfully) }
            },
            onError = {
                _uiState.update { it.copy(errorMessage = R.string.failed_to_edit_category ) }
            }
        )
    }

    fun deleteCategory(onSuccess: () -> Unit) {
        launchSafely(
            onSuccess = {
                taskServices.deleteCategory(_uiState.value.categoryId)
                onSuccess()
            },
            onError = {
                _uiState.update { it.copy(errorMessage =  R.string.failed_to_delete_category)}
            }
        )
    }

    private fun getImageUrl(url: String): String {
        return if (url.startsWith("content")) {
            url
        } else {
            url.getLastPartAfterSlash()
        }
    }

    private fun addCategory() {
        launchSafely(
            onSuccess = {
                val category = Category(
                    title = _uiState.value.categoryName,
                    imageUrl = _uiState.value.imageUri.toString(),
                    tasksCount = 0,
                    isPredefined = false
                )
                taskServices.addCategory(category)
                _uiState.update { it.copy(successMessage = R.string.category_added_successfully) }
            },
            onError = {
                _uiState.update {
                    it.copy(errorMessage = R.string.failed_to_add_category)
                }
            }
        )
    }


}

fun String.getLastPartAfterSlash(): String {
    return this.split("/").last()
}

