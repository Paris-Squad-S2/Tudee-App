package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskServices {
    fun getAllTasks(): Flow<List<Task>>
    fun getAllCategories(): Flow<List<Category>>
    suspend fun addTask(task: Task)
    suspend fun editTask(task: Task)
    suspend fun deleteTask(taskId: Long)
    fun getTaskById(taskId: Long): Flow<Task>
    suspend fun deleteCategory(categoryId: Long)
    fun getCategoryById(categoryId: Long): Flow<Category>
    suspend fun loadPredefinedCategories()
    suspend fun addCategory(category:Category)
}