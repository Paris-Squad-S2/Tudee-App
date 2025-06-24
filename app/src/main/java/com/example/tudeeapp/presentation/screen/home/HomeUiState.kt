package com.example.tudeeapp.presentation.screen.home


data class HomeUiState(
    val inProgressTasks: List<TaskUiState> = emptyList(),
    val toDoTasks: List<TaskUiState> = emptyList(),
    val doneTasks: List<TaskUiState> = emptyList(),
    val isTasksEmpty: Boolean = true,
    val isDarkMode: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

