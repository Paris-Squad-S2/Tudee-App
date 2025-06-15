package com.example.tudeeapp.data

import com.example.tudeeapp.data.mapper.toCategory
import com.example.tudeeapp.data.mapper.toCategoryEntity
import com.example.tudeeapp.data.mapper.toTask
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.TaskException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : TaskServices {

    override fun getAllTasks(): List<Task> {
        return taskDao.getAll().map { it.toTask() }
            .ifEmpty { throw TaskException("No tasks found") }
    }

    override fun getAllCategories(): List<Category> {
        return categoryDao.getAll().map { it.toCategory() }
            .ifEmpty { throw TaskException("No categories found") }
    }

    override fun insertPredefinedCategories(predefinedCategories: List<Category>) {
        categoryDao.insertPredefinedCategories(predefinedCategories.map { it.toCategoryEntity() })
    }


}