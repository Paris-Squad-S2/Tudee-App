package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.presentation.mapper.toResDrawables
import com.example.tudeeapp.presentation.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryFormViewModel(val taskServices: TaskServices, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow(CategoryFormState())
    val state: StateFlow<CategoryFormState> = _state.asStateFlow()

    val args = savedStateHandle.toRoute<Screens.CategoryFormEditScreen>()

    init {
        viewModelScope.launch {
            val category = taskServices.getCategoryById(args.categoryId).first()
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

    fun updateCategoryName(newCategoryName: String) {
        _state.update { it.copy(categoryName = newCategoryName) }
    }

    fun updateImage(uri: Uri) {
        _state.update { it.copy(imageUri = uri) }
    }


    fun editCategory() {
        viewModelScope.launch {
            taskServices.editCategory(
                id = state.value.categoryId,
                title = state.value.categoryName,
                imageUrl = getImageUrl(state.value.imageUri.toString())
            )
        }
    }


    private fun getImageUrl(url: String): String{
        if (url.startsWith("content")){
            return url
        }
        else{
            return url.getLastPartAfterSlash()
        }

    }
}

fun String.getLastPartAfterSlash(): String {
    return this.split("/").last()
}

