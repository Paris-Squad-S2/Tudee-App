package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri

data class CategoryFormState(
    val categoryName: String = "",
    val imageUri: Uri? = null,
    val isVisible: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
){
    val isFormValid: Boolean
        get() = categoryName.isNotBlank() && imageUri != null
}
