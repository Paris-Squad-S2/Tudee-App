package com.example.tudeeapp.data.mapper


import com.example.tudeeapp.data.MappingDateTimeException
import com.example.tudeeapp.data.MappingException
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.datetime.LocalDate


fun String.toTaskPriority(): TaskPriority = when (this.uppercase()) {
    "LOW" -> TaskPriority.LOW
    "MEDIUM" -> TaskPriority.MEDIUM
    "HIGH" -> TaskPriority.HIGH
    else -> throw MappingException(this.uppercase())
}

fun String.toTaskStatus(): TaskStatus = when (this.uppercase()) {
    "TO_DO" -> TaskStatus.TO_DO
    "IN_PROGRESS" -> TaskStatus.IN_PROGRESS
    "DONE" -> TaskStatus.DONE
    else -> throw MappingException(this.uppercase())
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.toTaskPriority(),
        status = this.status.toTaskStatus(),
        createdDate = LocalDate.parse(this.date),
        categoryId = this.categoryId,
    ).also { if(it.createdDate.toString().isEmpty()) throw MappingDateTimeException("${it.createdDate}") }
}


fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        priority = this.priority.name,
        status = this.status.name,
        date = this.createdDate.toString().substring(0,10),
        categoryId = this.categoryId
    ).also { if(it.date.isEmpty()) throw MappingDateTimeException(it.date) }
}


fun CategoryEntity.toCategory(): Category {
    return Category(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        tasksCount = this.tasksCount,
        isPredefined = false
    )
}


