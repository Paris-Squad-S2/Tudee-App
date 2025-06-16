package com.example.tudeeapp.presentation.screen.categories.viewModel

import androidx.lifecycle.ViewModel
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.screen.categories.viewModel.state.CategoryItemUIState
import com.example.tudeeapp.presentation.screen.categories.viewModel.state.CategoryUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CategoriesViewModel(): ViewModel() {
    private val _state = MutableStateFlow(CategoryUIState())
    val state = _state.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        _state.update { it.copy(
            categories = listOf(
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10),
                CategoryItemUIState(name = "Education", imageResId = R.drawable.ic_education, count = 10)
            )
        ) }
    }
}