package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskServices {
   fun getAllTasks(): Flow<List<Task>>
   fun getTaskById(id: Long): Flow<Task>
   fun getAllCategories(): Flow<List<Category>>
   fun getCategoryById(id: Long): Flow<Category>
   suspend fun loadPredefinedCategories()
   suspend fun updateTaskStatus(id: Long, newStatus: TaskStatus)
}