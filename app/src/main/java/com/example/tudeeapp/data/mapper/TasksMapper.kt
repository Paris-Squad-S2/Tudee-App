package com.example.tudeeapp.data.mapper


import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus


fun String.toTaskPriority(): TaskPriority = when(this.uppercase()) {
    "LOW" -> TaskPriority.LOW
    "MEDIUM" -> TaskPriority.MEDIUM
    "HIGH" -> TaskPriority.HIGH
    else -> TaskPriority.LOW
}

fun String.toTaskStatus(): TaskStatus = when (this.uppercase()) {
    "TO DO" -> TaskStatus.TO_DO
    "IN PROGRESS" -> TaskStatus.IN_PROGRESS
    "DONE" -> TaskStatus.DONE
    else -> TaskStatus.TO_DO
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.toTaskPriority(),
        status = this.status.toTaskStatus(),
        createdDate = this.date,
        category = this.categoryId ,
    )
}