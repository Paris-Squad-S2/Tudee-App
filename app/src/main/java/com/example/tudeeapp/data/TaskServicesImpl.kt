package com.example.tudeeapp.data

import com.example.tudeeapp.data.mapper.DataConstant
import com.example.tudeeapp.data.mapper.toCategory
import com.example.tudeeapp.data.mapper.toCategoryEntity
import com.example.tudeeapp.data.mapper.toTask
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.NoCategoriesFoundException
import com.example.tudeeapp.domain.exception.NoTasksFoundException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val appPreferences: AppPreferences,
    private val dataConstant: DataConstant
) : TaskServices {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAll()
            .map { list -> list.map { it.toTask() } }
            .catch { throw NoTasksFoundException()  }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAll()
            .map { list -> list.map { it.toCategory() } }
            .catch { throw NoCategoriesFoundException() }
    }

    override suspend fun loadPredefinedCategories() {
        try {
            if (appPreferences.isAppLaunchForFirstTime()) {
                categoryDao.insertPredefinedCategories(dataConstant.predefinedCategories.map { it.toCategoryEntity() })
                appPreferences.setAppLaunchIsDone()
            }
        } catch (e: Exception) {
            throw NoCategoriesFoundException()
        }
    }

    override suspend fun getCategoryById(id: Long): Category = categoryDao.findById(id).first().toCategory()

}