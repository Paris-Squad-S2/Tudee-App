package com.example.tudeeapp.presentation.screen.task.mapper

import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.screen.task.TaskItemUiState
import com.example.tudeeapp.presentation.screen.task.TaskPriorityUi
import com.example.tudeeapp.presentation.screen.task.TaskStatusUi

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
    TaskStatus.TO_DO -> TaskStatusUi.TO_DO
    TaskStatus.IN_PROGRESS -> TaskStatusUi.IN_PROGRESS
    TaskStatus.DONE -> TaskStatusUi.DONE
}