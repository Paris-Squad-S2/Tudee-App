package com.example.tudeeapp.data

import com.example.tudeeapp.data.mapper.toCategory
import com.example.tudeeapp.data.mapper.toCategoryEntity
import com.example.tudeeapp.data.mapper.toTask
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.CategoryException
import com.example.tudeeapp.domain.exception.TaskException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val appPreferences: AppPreferences,
    private val dataConstant: DataConstant
) : TaskServices {

    override suspend fun getAllTasks(): Flow<List<Task>> {
        try {
            return taskDao.getAll().map { it.map { it.toTask() } }
        } catch (e: DataException) {
            throw TaskException(e.message.toString())
        }
    }

    override suspend fun getAllCategories(): Flow<List<Category>> {
        try {
            return categoryDao.getAll().map { it.map { it.toCategory() } }
        } catch (e: DataException) {
            throw CategoryException(e.message.toString())
        }

    }

    override suspend fun loadPredefinedCategories() {
        if (appPreferences.isFirstLaunch()) {
            categoryDao.insertPredefinedCategories(dataConstant.predefinedCategories.map { it.toCategoryEntity() })
            appPreferences.setFirstLaunchDone()
        }
    }


}