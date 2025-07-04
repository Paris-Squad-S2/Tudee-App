package com.example.tudeeapp.data

import com.example.tudeeapp.data.source.local.DataConstant
import com.example.tudeeapp.data.mapper.toCategory
import com.example.tudeeapp.data.mapper.toCategoryEntity
import com.example.tudeeapp.data.mapper.toTask
import com.example.tudeeapp.data.mapper.toTaskEntity
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.AddCategoryException
import com.example.tudeeapp.domain.exception.CategoriesNotFoundException
import com.example.tudeeapp.domain.exception.CategoryNotFoundException
import com.example.tudeeapp.domain.exception.NoCategoryDeletedException
import com.example.tudeeapp.domain.exception.NoCategoryEditedException
import com.example.tudeeapp.domain.exception.NoTaskAddedException
import com.example.tudeeapp.domain.exception.NoTaskDeletedException
import com.example.tudeeapp.domain.exception.NoTaskEditedException
import com.example.tudeeapp.domain.exception.TaskNotFoundException
import com.example.tudeeapp.domain.exception.TasksNotFoundException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class TaskServicesImpl(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao,
    private val dataConstant: DataConstant
) : TaskServices {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { tasks -> tasks.map { it.toTask() } }
            .catch { throw TasksNotFoundException() }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
            .onEach { categories -> if (categories.isEmpty()) loadPredefinedCategories() }
            .map { categories -> categories.map { it.toCategory() } }
            .catch { throw CategoriesNotFoundException() }
    }


    override suspend fun addTask(task: Task) {
        try {
            taskDao.addOrUpdateTask(task.toTaskEntity())
        } catch (_: Exception) {
            throw NoTaskAddedException()
        }
    }

    override suspend fun editTask(task: Task) {
        try {
            taskDao.addOrUpdateTask(task.toTaskEntity())
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
            taskDao.deleteTasksByCategoryId(categoryId)
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
            categoryDao.insertPredefinedCategories(dataConstant.predefinedCategories.map { it.toCategoryEntity() })
        } catch (_: Exception) {
            throw CategoriesNotFoundException()
        }
    }

    override suspend fun addCategory(category:Category) {
        try {
            categoryDao.insertOrUpdateCategory(category.toCategoryEntity())
        } catch (e: Exception) {
            throw AddCategoryException()
        }
    }

    override suspend fun editCategory(id: Long, title: String, imageUri: String) {

        try {
            val currentCategory: CategoryEntity =
                categoryDao.getCategoryById(id).firstOrNull() ?: throw CategoryNotFoundException()

            val updatedCategory = currentCategory.copy(
                title = title,
                imageUri = imageUri
            )

            categoryDao.insertOrUpdateCategory(updatedCategory)
        } catch (e: Exception) {
            throw NoCategoryEditedException()
        }

    }

}