package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri

data class CategoryFormUIState(
    val categoryName: String = "",
    val categoryId: Long = 0,
    val imageUri: Uri? = null,
    val errorMessage: Int? = null,
    val successMessage: Int? = null
){
    val isFormValid: Boolean
        get() = categoryName.isNotBlank() && imageUri != null
}
