package com.example.tudeeapp.presentation.screen.categoriesForm

import coil3.Uri

interface CategoryFormInteractionListener {
    fun onCategoryNameChanged(newName : String)
    fun onImageSelected(uri: Uri)
    fun onSubmit()
    fun onDelete()
    fun onCancel()
}