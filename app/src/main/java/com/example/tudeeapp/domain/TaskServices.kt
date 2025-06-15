package com.example.tudeeapp.domain

import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.Category

interface TaskServices {
    fun getAllTasks(): List<Task>
    fun getAllCategories(): List<Category>
}