package com.example.tudeeapp

import com.example.tudeeapp.data.TaskServicesImpl
import com.example.tudeeapp.data.mapper.DataConstant
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.exception.NoCategoriesFoundException
import com.example.tudeeapp.domain.exception.NoTasksFoundException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TaskServicesImplTest {
    private lateinit var taskServices: TaskServicesImpl
    private lateinit var taskDao: TaskDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var appPreferences: AppPreferences
    private lateinit var dataConstant: DataConstant

    @BeforeEach
    fun setUp() {
        taskDao = mockk(relaxed = true)
        categoryDao = mockk(relaxed = true)
        appPreferences = mockk()
        dataConstant = mockk()
        taskServices = TaskServicesImpl(taskDao, categoryDao, appPreferences, dataConstant)
    }

    @Test
    fun `getTasks should return tasks when getAll in TaskDao called successfully`() = runTest {
        coEvery { taskDao.getAll() } returns flowOf(dummyTaskEntities)

        val result = taskServices.getAllTasks().first()

        assertEquals(expectedTasks[0].title, result[0].title)
    }

    @Test
    fun `getTasks should throw exception when TaskDao fails`() = runTest {
        coEvery { taskDao.getAll() } returns flowOf(emptyList())

        assertThrows< NoTasksFoundException > {
            taskServices.getAllTasks().toList()
        }
    }
}