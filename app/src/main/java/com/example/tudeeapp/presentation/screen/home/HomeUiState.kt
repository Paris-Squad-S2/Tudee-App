package com.example.tudeeapp.presentation.screen.home

data class HomeUiState(
    val inProgressTasks: List<TaskUi> = emptyList(),
    val toDoTasks: List<TaskUi> = emptyList(),
    val doneTasks: List<TaskUi> = emptyList(),
    val isTasksEmpty: Boolean = true,
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

