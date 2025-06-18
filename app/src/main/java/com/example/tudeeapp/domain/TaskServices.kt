package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskServices {
    fun getAllTasks(): Flow<Result<List<Task>>>
    fun getAllCategories(): Flow<Result<List<Category>>>
    fun addTask(task: Task): Flow<Result<Unit>>
    fun editTask(task: Task): Flow<Result<Unit>>
    fun deleteTask(taskId: Long): Flow<Result<Unit>>
    fun getTaskById(taskId: Long): Flow<Result<Task>>
    fun deleteCategory(categoryId: Long): Flow<Result<Unit>>
    fun getCategoryById(categoryId: Long): Flow<Result<Category>>
    fun loadPredefinedCategories() : Flow<Result<Unit>>
}