package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import androidx.navigation.toRoute
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.presentation.common.extentions.getLastPartAfterSlash
import com.example.tudeeapp.presentation.mapper.toResDrawables
import com.example.tudeeapp.presentation.screen.base.BaseViewModel
import com.example.tudeeapp.presentation.navigation.Destinations
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class CategoryFormViewModel(
    val taskServices: TaskServices,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CategoryFormUIState>(CategoryFormUIState()), CategoryFormInteractionListener {

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
                        imageUri = if (category.imageUri.startsWith("R.drawable.")) {
                            val resourceId = category.imageUri.toResDrawables()
                            "android.resource://com.example.tudeeapp/$resourceId".toUri()
                        } else {
                            category.imageUri.toUri()
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

    private fun editCategory() {
        launchSafely(
            onSuccess = {
                taskServices.editCategory(
                    id = _uiState.value.categoryId,
                    title = _uiState.value.categoryName,
                    imageUri = getImageUrl(_uiState.value.imageUri.toString())
                )
                _uiState.update { it.copy(successMessage = R.string.edited_category_successfully) }
            },
            onError = {
                _uiState.update { it.copy(errorMessage = R.string.failed_to_edit_category) }
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
                    imageUri = _uiState.value.imageUri.toString(),
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

    override fun onCategoryNameChanged(newName: String) {
        _uiState.update { it.copy(categoryName = newName) }
    }

    override fun onImageSelected(uri: Uri) {
        _uiState.update { it.copy(imageUri = uri) }
    }

    override fun onSubmit() {
        if (_uiState.value.categoryId != 0L) {
            editCategory()
        } else {
            addCategory()
        }
    }

    override fun onDelete() {
        launchSafely(
            onSuccess = {
                taskServices.deleteCategory(_uiState.value.categoryId)
                navigate(
                    Destinations.Category,
                    NavOptions.Builder()
                        .setPopUpTo(Destinations.Category, inclusive = false)
                        .build()
                )
            },
            onError = {
                _uiState.update { it.copy(errorMessage = R.string.failed_to_delete_category) }
            }
        )
    }

    override fun onCancel() {
        navigateUp()
    }

}

