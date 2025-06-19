package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskServices {
    fun getAllTasks(): Flow<List<Task>>
    fun getAllCategories(): Flow<List<Category>>
    suspend fun editTask(task: Task)
    fun getTaskById(taskId: Long): Flow<Task>
    fun getCategoryById(categoryId: Long): Flow<Category>
    suspend fun loadPredefinedCategories()
}