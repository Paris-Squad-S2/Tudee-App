package com.example.tudeeapp.data

import com.example.tudeeapp.data.mapper.toTask
import com.example.tudeeapp.data.mapper.toTaskCategory
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.TaskException
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskCategory

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : TaskServices {

    init {
        insertPredefinedCategories()
    }

    override fun getAllTasks(): List<Task> {
        return taskDao.getAll().map { it.toTask() }
            .ifEmpty { throw TaskException("No tasks found") }
    }

    override fun getAllCategories(): List<TaskCategory> {
        return categoryDao.getAll().map { it.toTaskCategory() }
            .ifEmpty { throw TaskException("No categories found") }
    }

    private fun insertPredefinedCategories() {
        categoryDao.insertList(
            listOf(
                CategoryEntity(title = "Education", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Shopping", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Medical", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Gym", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Entertainment", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Cooking", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Family & friend", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Traveling", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Agriculture", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Coding", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Adoration", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Fixing bugs", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Cleaning", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Work", imageUri = "", isPredefined = true),
                CategoryEntity(title = "Budgeting", imageUri = "", isPredefined = true),
            )
        )
    }
}