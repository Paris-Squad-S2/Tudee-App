package com.example.tudeeapp.presentation.screen.taskForm

import kotlinx.datetime.LocalDate

interface InteractionListener {
    fun onTitleChange(title: String)
    fun onDescriptionChange(description: String)
    fun onDateClicked(isVisible: Boolean)
    fun onDateSelected(date: LocalDate)
    fun onPrioritySelected(priorityUiState: TaskPriorityUiState)
    fun onCategorySelected(categoryId: Long)
    fun onActionButtonClicked()
    fun popBackStack()
}