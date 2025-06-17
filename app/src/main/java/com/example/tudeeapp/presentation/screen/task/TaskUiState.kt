package com.example.tudeeapp.presentation.screen.task

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed class TaskUiState{
    data class success(val data:TasksUi):TaskUiState()
    object isLoading:TaskUiState()
    data class error(val message:String):TaskUiState()
}

data class TasksUi(
    val tasks: List<TaskItemUiState> = emptyList(),
    val selectedStatus: TaskStatusUi = TaskStatusUi.TO_DO,
    val selectedDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val daysOfMonth: List<LocalDate> = emptyList(),
    val currentMonthYear: String = "",
    val todayIndex: Int = 0,
    val showDatePicker: Boolean = false,
)

data class TaskItemUiState(
    val id: Long,
    val title: String,
    val description: String,
    val priority: TaskPriorityUi,
    val status: TaskStatusUi,
    val createdDate: LocalDate,
    val categoryId: Long
)

enum class TaskPriorityUi {
    HIGH,
    MEDIUM,
    LOW
}

enum class TaskStatusUi {
    TO_DO,
    IN_PROGRESS,
    DONE
}