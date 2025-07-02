package com.example.tudeeapp.presentation.screen.categories

import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

val dummyTaskEntities = listOf(
    TaskEntity(
        id = 1,
        title = "Test Task 1",
        description = "Description 1",
        date = LocalDateTime(LocalDate(2023, 1, 1)
            , LocalTime(1, 1, 1, 1)),
        status = "TO_DO",
        priority = "HIGH",
        categoryId = 1
    ),
    TaskEntity(
        id = 2,
        title = "Test Task 2",
        description = "Description 2",
        date = LocalDateTime(LocalDate(2022, 2, 2)
            , LocalTime(2, 2, 2, 2)),
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
        createdDate = LocalDateTime(LocalDate(2023, 1, 1)
            , LocalTime(1, 1, 1, 1)),
        status = TaskStatus.TO_DO,
        priority = TaskPriority.HIGH,
        categoryId = 1
    ),
    Task(
        id = 2,
        title = "Test Task 2",
        description = "Description 2",
        createdDate = LocalDateTime(LocalDate(2013, 3, 3)
            , LocalTime(1, 3, 3, 3)),
        status = TaskStatus.IN_PROGRESS,
        priority = TaskPriority.MEDIUM,
        categoryId = 2
    )
)

val dummyCategoryEntities = listOf(
    CategoryEntity(
        id = 1,
        title = "Work",
        imageUri = "https://example.com/work.png",
        isPredefined = true,
        tasksCount = 5
    ),
    CategoryEntity(
        id = 2,
        title = "Personal",
        imageUri = "https://example.com/personal.png",
        isPredefined = false,
        tasksCount = 3
    )
)

val expectedCategories = listOf(
    Category(
        id = 1,
        title = "Work",
        imageUri = "R.drawable.eduction",
        isPredefined = true,
        tasksCount = 5
    ),
    Category(
        id = 2,
        title = "Personal",
        imageUri = "R.drawable.eduction",
        isPredefined = false,
        tasksCount = 3
    )
)
