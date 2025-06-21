package com.example.tudeeapp.data

import com.example.tudeeapp.data.mapper.DataConstant
import com.example.tudeeapp.data.mapper.toTaskEntity
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.exception.NoTaskAddedException
import com.example.tudeeapp.domain.exception.NoTaskEditedException
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TaskServicesImplTest {
    private lateinit var taskServices: TaskServicesImpl
    private val taskDao: TaskDao = mockk(relaxed = true)
    private val categoryDao: CategoryDao = mockk(relaxed = true)
    private val appPreferences: AppPreferences = mockk()
    private val dataConstant: DataConstant = mockk()

    @BeforeEach
    fun setUp() {
        taskServices = TaskServicesImpl(taskDao, categoryDao, appPreferences, dataConstant)
    }

    @Test
    fun `addTask() should add task successfully when valid task is provided`() = runTest {
        coEvery { taskDao.addTask(any()) } returns Unit

        taskServices.addTask(sampleTask)

        coVerify { taskDao.addTask(sampleTask.toTaskEntity()) }
    }

    @Test
    fun `addTask() should throw NoTaskAddedException when task addition fails`() = runTest {
        coEvery { taskDao.addTask(any()) } throws Exception("Database error")

        val exception = assertThrows<NoTaskAddedException> {
            taskServices.addTask(sampleTask)
        }

        assertThat(exception).isInstanceOf(NoTaskAddedException::class.java)
    }

    @Test
    fun `editTask() should edit task successfully when valid task is provided`() = runTest {
        coEvery { taskDao.editTask(any()) } returns Unit

        taskServices.editTask(sampleTask)

        coVerify { taskDao.editTask(sampleTask.toTaskEntity()) }
    }

    @Test
    fun `editTask() should throw NoTaskEditedException when task editing fails`() = runTest {
        coEvery { taskDao.editTask(any()) } throws Exception("Database error")

        val exception = assertThrows<NoTaskEditedException> {
            taskServices.editTask(sampleTask)
        }

        assertThat(exception).isInstanceOf(NoTaskEditedException::class.java)
    }

    @Test
    fun `addTask() should add task with empty title and description`() = runTest {
        val edgeCaseTask = sampleTask.copy(title = "", description = "")
        coEvery { taskDao.addTask(any()) } returns Unit

        taskServices.addTask(edgeCaseTask)

        coVerify { taskDao.addTask(edgeCaseTask.toTaskEntity()) }
    }

    private val sampleTask = Task(
        id = 1L,
        title = "Test Task",
        description = "Test Description",
        priority = TaskPriority.HIGH,
        status = TaskStatus.TO_DO,
        createdDate = LocalDate(2023, 1, 1),
        categoryId = 1L
    )
}
