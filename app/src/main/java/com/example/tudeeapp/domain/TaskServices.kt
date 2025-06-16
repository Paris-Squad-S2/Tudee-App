package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface TaskServices {
    suspend fun getAllTasks(): Flow<List<Task>>
    suspend fun getAllCategories(): Flow<List<Category>>
    suspend fun loadPredefinedCategories()
}