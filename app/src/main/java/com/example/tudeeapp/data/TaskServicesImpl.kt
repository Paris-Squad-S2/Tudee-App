package com.example.tudeeapp.data

import com.example.tudeeapp.data.exception.DataException
import com.example.tudeeapp.data.mapper.DataConstant
import com.example.tudeeapp.data.mapper.toCategory
import com.example.tudeeapp.data.mapper.toCategoryEntity
import com.example.tudeeapp.data.mapper.toTask
import com.example.tudeeapp.data.mapper.toTaskEntity
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.CategoryException
import com.example.tudeeapp.domain.exception.TaskException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val appPreferences: AppPreferences,
    private val dataConstant: DataConstant
) : TaskServices {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { tasks -> tasks.map { it.toTask() } }
            .catch { throw TaskException() }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
            .map { categories -> categories.map { it.toCategory() } }
            .catch { throw CategoryException() }
    }

    override suspend fun addTask(task: Task) {
        try {
            taskDao.addTask(task.toTaskEntity())
        } catch (e: Exception) {
            throw TaskException()
        }
    }

    override suspend fun editTask(task: Task) {
        try {
            taskDao.editTask(task.toTaskEntity())
        } catch (e: Exception) {
            throw TaskException()
        }
    }

    override suspend fun deleteTask(taskId: Long) {
        try {
            taskDao.deleteTask(taskId)
        } catch (e: Exception) {
            throw TaskException()
        }
    }

    override fun getTaskById(taskId: Long): Flow<Task> {
        return taskDao.getTaskById(taskId)
            .map { it.toTask() }
            .catch { throw TaskException() }
    }

    override suspend fun deleteCategory(categoryId: Long) {
        try {
            categoryDao.deleteCategory(categoryId)
        } catch (e: Exception) {
            throw TaskException()
        }
    }

    override fun getCategoryById(categoryId: Long): Flow<Category> {
        return categoryDao.getCategoryById(categoryId)
            .map { it.toCategory() }
            .catch { throw CategoryException() }
    }

    override suspend fun loadPredefinedCategories() {
        try {
            if (appPreferences.isAppLaunchForFirstTime()) {
                categoryDao.insertPredefinedCategories(dataConstant.predefinedCategories.map { it.toCategoryEntity() })
                appPreferences.setAppLaunchIsDone()
            }
        } catch (e: Exception) {
            throw CategoryException()
        }
    }

    override suspend fun addCategory(title: String, imageUrl: String) {

        try{
            val category = Category(
                title = title,
                imageUrl = imageUrl,
                tasksCount = 0,
                isPredefined = false
            ).toCategoryEntity()
            categoryDao.insert(category)
        }catch (e: DataException){
            throw CategoryException()
        }

    }

    override suspend fun editCategory(id: Long, title: String, imageUrl: String) {

        try {
            val currentCategory: CategoryEntity = categoryDao.findById(id).firstOrNull() ?: throw CategoryException()

            val updatedCategory = currentCategory.copy(
                title = title,
                imageUrl = imageUrl
            )

            categoryDao.update(updatedCategory)
        }catch (e: DataException){
            throw CategoryException()
        }

    }

}