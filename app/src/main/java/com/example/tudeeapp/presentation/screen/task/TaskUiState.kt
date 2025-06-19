package com.example.tudeeapp.presentation.screen.task

import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.utills.TaskPriorityUi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TaskUiState(
    val data: TasksUi = TasksUi(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class TasksUi(
    val calender: CalenderUi = CalenderUi(),
    val status: List<StatusUi> = emptyList(),
    val selectedStatus: TaskStatusUi = TaskStatusUi.TO_DO,
    val tasks: List<TaskItemUiState> = emptyList(),
    val showDatePicker: Boolean = false,
    val todayIndex:Int = 0
)

data class CalenderUi(
    val currentMonthYear: String = "",
    val daysOfMonth: List<DayUi> = emptyList(),
    val selectedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date
)

data class DayUi(
    val num: Int,
    val name: String,
    val date: LocalDate
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

data class StatusUi(
    val name:TaskStatusUi = TaskStatusUi.TO_DO,
    val count:Int = 0,
    val isSelected:Boolean = false
)

enum class TaskStatusUi(val stringResId: Int) {
    IN_PROGRESS(R.string.status_in_progress),
    TO_DO(R.string.status_to_do),
    DONE(R.string.status_done)
}

fun List<Task>.toTasksUi(): List<TaskItemUiState> {
    return this.map { it.toTaskUiState() }
}

fun Task.toTaskUiState(): TaskItemUiState {
    return TaskItemUiState(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.toUiPriority(),
        status = this.status.toUiStatus(),
        createdDate = this.createdDate,
        categoryId = this.categoryId
    )
}

fun TaskPriority.toUiPriority(): TaskPriorityUi = when (this) {
    TaskPriority.HIGH -> TaskPriorityUi.HIGH
    TaskPriority.MEDIUM -> TaskPriorityUi.MEDIUM
    TaskPriority.LOW -> TaskPriorityUi.LOW
}

fun TaskStatus.toUiStatus(): TaskStatusUi = when (this) {
    TaskStatus.IN_PROGRESS -> TaskStatusUi.IN_PROGRESS
    TaskStatus.TO_DO -> TaskStatusUi.TO_DO
    TaskStatus.DONE -> TaskStatusUi.DONE
}

fun LocalDate.createCalendarUi(
    currentMonthYear:String,
    daysOfMonth:List<DayUi>,
): CalenderUi {
    return CalenderUi(
        currentMonthYear = currentMonthYear,
        daysOfMonth = daysOfMonth,
        selectedDate = this
    )
}