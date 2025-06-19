package com.example.tudeeapp.data

import com.example.tudeeapp.data.mapper.DataConstant
import com.example.tudeeapp.data.mapper.toCategory
import com.example.tudeeapp.data.mapper.toCategoryEntity
import com.example.tudeeapp.data.mapper.toTask
import com.example.tudeeapp.data.mapper.toTaskEntity
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.CategoriesNotFoundException
import com.example.tudeeapp.domain.exception.CategoryNotFoundException
import com.example.tudeeapp.domain.exception.NoCategoryDeletedException
import com.example.tudeeapp.domain.exception.NoTaskAddedException
import com.example.tudeeapp.domain.exception.NoTaskDeletedException
import com.example.tudeeapp.domain.exception.TaskNotFoundException
import com.example.tudeeapp.domain.exception.TasksNotFoundException
import com.example.tudeeapp.domain.exception.NoTaskEditedException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val appPreferences: AppPreferences,
    private val dataConstant: DataConstant
) : TaskServices {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAll()
            .map { list ->
                if (list.isEmpty()) {
                    throw NoTasksFoundException()
                }
                list.map { it.toTask() }
            }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAll()
            .map { list ->
                if (list.isEmpty()) {
                    throw NoCategoriesFoundException()
                }
                list.map { it.toCategory() }
            }

        return categoryDao.getAllCategories()
            .map { categories -> categories.map { it.toCategory() } }
            .catch { throw CategoriesNotFoundException() }
    }

    override suspend fun addTask(task: Task) {
        try {
            taskDao.addTask(task.toTaskEntity())
        } catch (_: Exception) {
            throw NoTaskAddedException()
        }
    }

    override suspend fun editTask(task: Task) {
        try {
            taskDao.editTask(task.toTaskEntity())
        } catch (_: Exception) {
            throw NoTaskEditedException()
        }
    }

    override suspend fun deleteTask(taskId: Long) {
        try {
            taskDao.deleteTask(taskId)
        } catch (_: Exception) {
            throw NoTaskDeletedException()
        }
    }

    override fun getTaskById(taskId: Long): Flow<Task> {
        return taskDao.getTaskById(taskId)
            .map { it.toTask() }
            .catch { throw TaskNotFoundException() }
    }

    override suspend fun deleteCategory(categoryId: Long) {
        try {
            categoryDao.deleteCategory(categoryId)
        } catch (_: Exception) {
            throw NoCategoryDeletedException()
        }
    }

    override fun getCategoryById(categoryId: Long): Flow<Category> {
        return categoryDao.getCategoryById(categoryId)
            .map { it.toCategory() }
            .catch { throw CategoryNotFoundException() }
    }

    override suspend fun loadPredefinedCategories() {
        try {
            if (appPreferences.isAppLaunchForFirstTime()) {
                categoryDao.insertPredefinedCategories(dataConstant.predefinedCategories.map { it.toCategoryEntity() })
                appPreferences.setAppLaunchIsDone()
            }
        } catch (_: Exception) {
            throw CategoriesNotFoundException()
        }
    }
}