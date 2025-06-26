package com.example.tudeeapp.data.mapper

import com.example.tudeeapp.data.exception.MappingPriorityException
import com.example.tudeeapp.data.exception.MappingStatusException
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus


fun String.toTaskPriority(): TaskPriority = when (this.uppercase()) {
    "LOW" -> TaskPriority.LOW
    "MEDIUM" -> TaskPriority.MEDIUM
    "HIGH" -> TaskPriority.HIGH
    else -> throw MappingPriorityException(this.uppercase())
}

fun String.toTaskStatus(): TaskStatus = when (this.uppercase()) {
    "TO_DO" -> TaskStatus.TO_DO
    "IN_PROGRESS" -> TaskStatus.IN_PROGRESS
    "DONE" -> TaskStatus.DONE
    else -> throw MappingStatusException(this.uppercase())
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.toTaskPriority(),
        status = this.status.toTaskStatus(),
        createdDate = this.date,
        categoryId = this.categoryId,
    )
}


fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.name,
        status = this.status.name,
        date = this.createdDate,
        categoryId = this.categoryId
    )
}


fun CategoryEntity.toCategory(): Category {
    return Category(
        id = this.id,
        title = this.title,
        imageUri = this.imageUri,
        tasksCount = this.tasksCount,
        isPredefined = this.isPredefined
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        title = this.title,
        imageUri = this.imageUri,
        tasksCount = this.tasksCount,
        isPredefined = this.isPredefined
    )
}


