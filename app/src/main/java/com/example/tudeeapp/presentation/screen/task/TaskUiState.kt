package com.example.tudeeapp.presentation.screen.task

import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.Category
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
    val category: CategoryUiState
)

data class StatusUi(
    val name:TaskStatusUi = TaskStatusUi.TO_DO,
    val count:Int = 0,
    val isSelected:Boolean = false
)

data class CategoryUiState(
    val id: Long = 1L,
    val iconRes: String = "",
    val isPredefined: Boolean = false
)

enum class TaskStatusUi(val stringResId: Int) {
    IN_PROGRESS(R.string.in_progress_text),
    TO_DO(R.string.to_do),
    DONE(R.string.done);

    companion object {
        private fun fromNameOrNull(name: String): TaskStatusUi? {
            return entries.find { it.name.equals(name, ignoreCase = true) }
        }

        fun fromNameOrDefault(name: String, default: TaskStatusUi = TO_DO): TaskStatusUi {
            return fromNameOrNull(name) ?: default
        }
    }
}

fun Task.toTaskUiState(): TaskItemUiState {
    return TaskItemUiState(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.toUiPriority(),
        status = this.status.toUiStatus(),
        createdDate = this.createdDate,
        category = CategoryUiState()
    )
}

fun Category.toCategoryUi(): CategoryUiState{
    return CategoryUiState(
        id = this.id,
        iconRes = this.imageUri,
        isPredefined = this.isPredefined
    )
}
fun TaskPriority.toUiPriority(): TaskPriorityUi = when (this) {
    TaskPriority.HIGH -> TaskPriorityUi.High
    TaskPriority.MEDIUM -> TaskPriorityUi.Medium
    TaskPriority.LOW -> TaskPriorityUi.Low
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