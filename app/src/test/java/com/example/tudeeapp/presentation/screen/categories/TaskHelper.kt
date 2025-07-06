package com.example.tudeeapp.presentation.screen.categories

import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.screen.home.utils.getToday
import kotlinx.datetime.LocalDateTime

val dummyTaskEntities = listOf(
    TaskEntity(
        id = 1,
        title = "Test Task 1",
        description = "Description 1",
        date =  LocalDateTime.parse("2024-01-01"+"T"+ getToday().time.toString()),
        status = "TO_DO",
        priority = "HIGH",
        categoryId = 1
    ),
    TaskEntity(
        id = 2,
        title = "Test Task 2",
        description = "Description 2",
        date = LocalDateTime.parse("2024-01-16"+"T"+ getToday().time.toString()),
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
        createdDate = LocalDateTime.parse("2024-01-15"+"T"+  getToday().time.toString()),
        status = TaskStatus.TO_DO,
        priority = TaskPriority.HIGH,
        categoryId = 1
    ),
    Task(
        id = 2,
        title = "Test Task 2",
        description = "Description 2",
        createdDate = LocalDateTime.parse("2024-01-16"+"T"+ getToday().time.toString()),
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