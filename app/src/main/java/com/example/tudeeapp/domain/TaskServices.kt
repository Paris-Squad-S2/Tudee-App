package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface TaskServices {
    fun getAllTasks(): Flow<List<Task>>
    fun getAllCategories(): Flow<List<Category>>
    suspend fun addCategory(title: String, imageUrl: String)
    suspend fun editCategory(id: Long, title: String, imageUrl: String)
    suspend fun addTask(task: Task)
    suspend fun editTask(task: Task)
    suspend fun deleteTask(taskId: Long)
    fun getTaskById(taskId: Long): Flow<Task>
    suspend fun deleteCategory(categoryId: Long)
    fun getCategoryById(categoryId: Long): Flow<Category>
    suspend fun loadPredefinedCategories()
}