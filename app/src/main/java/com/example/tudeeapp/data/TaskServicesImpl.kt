package com.example.tudeeapp.data

import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.domain.TaskServices

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
): TaskServices {

}