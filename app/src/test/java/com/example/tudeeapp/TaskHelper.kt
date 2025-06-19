package com.example.tudeeapp

import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.datetime.LocalDate

val dummyTaskEntities = listOf(
    TaskEntity(
        id = 1,
        title = "Test Task 1",
        description = "Description 1",
        date = "2024-01-15",
        status = "TO_DO",
        priority = "HIGH",
        categoryId = 1
    ),
    TaskEntity(
        id = 2,
        title = "Test Task 2",
        description = "Description 2",
        date = "2024-01-16",
        status = "IN_PROGRESS",
        priority = "MEDIUM",
        categoryId = 2
    )
)

val expectedTasks = listOf(
    Task(
        id = 1,
        title = "Test Task 1",
        description = "Description 1",
        createdDate = LocalDate(2024, 1, 15),
        status = TaskStatus.TO_DO,
        priority = TaskPriority.HIGH,
        categoryId = 1
    ),
    Task(
        id = 2,
        title = "Test Task 2",
        description = "Description 2",
        createdDate = LocalDate(2024, 1, 16),
        status = TaskStatus.IN_PROGRESS,
        priority = TaskPriority.MEDIUM,
        categoryId = 2
    )
)

val dummyCategoryEntities = listOf(
    CategoryEntity(
        id = 1,
        title = "Work",
        imageUrl = "https://example.com/work.png",
        isPredefined = true,
        tasksCount = 5
    ),
    CategoryEntity(
        id = 2,
        title = "Personal",
        imageUrl = "https://example.com/personal.png",
        isPredefined = false,
        tasksCount = 3
    )
)

val expectedCategories = listOf(
    Category(
        id = 1,
        title = "Work",
        imageUrl = "R.drawable.eduction",
        isPredefined = true,
        tasksCount = 5
    ),
    Category(
        id = 2,
        title = "Personal",
        imageUrl = "R.drawable.eduction",
        isPredefined = false,
        tasksCount = 3
    )
)
