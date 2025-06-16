package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface TaskServices {
   fun getAllTasks(): Flow<List<Task>>
   fun getAllCategories(): Flow<List<Category>>
   suspend fun loadPredefinedCategories()
}