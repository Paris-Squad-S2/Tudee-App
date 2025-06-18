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
import com.example.tudeeapp.domain.exception.NoCategoriesFoundException
import com.example.tudeeapp.domain.exception.NoTasksFoundException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val appPreferences: AppPreferences,
    private val dataConstant: DataConstant
) : TaskServices {

    override fun getAllTasks(): Flow<Result<List<Task>>> {
        return taskDao.getAllTasks()
            .map { tasks -> Result.success(tasks.map { it.toTask() }) }
            .catch { emit(Result.failure(NoTasksFoundException())) }
    }

    override fun getAllCategories(): Flow<Result<List<Category>>> {
        return categoryDao.getCategories()
            .map { categories -> Result.success(categories.map { it.toCategory() }) }
            .catch { emit(Result.failure(NoCategoriesFoundException())) }
    }

    override fun addTask(task: Task): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(taskDao.addTask(task.toTaskEntity())))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun editTask(task: Task): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(taskDao.editTask(task.toTaskEntity())))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun deleteTask(taskId: Long): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(taskDao.deleteTask(taskId)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getTaskById(taskId: Long): Flow<Result<Task>> {
        return taskDao.getTaskById(taskId)
            .map { task -> Result.success(task.toTask()) }
            .catch { emit(Result.failure(NoCategoriesFoundException())) }
    }

    override fun deleteCategory(categoryId: Long): Flow<Result<Unit>> = flow {
        try {
            emit(Result.success(categoryDao.deleteCategory(categoryId)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getCategoryById(categoryId: Long): Flow<Result<Category>> {
        return categoryDao.getCategoryById(categoryId)
            .map { category -> Result.success(category.toCategory()) }
            .catch { exception -> emit(Result.failure(exception)) }
    }

    override fun loadPredefinedCategories(): Flow<Result<Unit>> = flow {
        try {
            if (appPreferences.isAppLaunchForFirstTime()) {
                val categories = dataConstant.predefinedCategories.map { it.toCategoryEntity() }
                categoryDao.insertPredefinedCategories(categories)
                appPreferences.setAppLaunchIsDone()
            }
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(NoCategoriesFoundException()))
        }
    }

}