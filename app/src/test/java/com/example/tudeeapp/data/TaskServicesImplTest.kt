package com.example.tudeeapp.data

import com.example.tudeeapp.data.source.local.DataConstant
import com.example.tudeeapp.data.mapper.toCategoryEntity
import com.example.tudeeapp.data.mapper.toTaskEntity
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.domain.exception.AddCategoryException
import com.example.tudeeapp.domain.exception.NoCategoryDeletedException
import com.example.tudeeapp.domain.exception.NoCategoryEditedException
import com.example.tudeeapp.domain.exception.NoTaskAddedException
import com.example.tudeeapp.domain.exception.NoTaskEditedException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.screen.categories.dummyCategoryEntities
import com.example.tudeeapp.presentation.screen.categories.dummyTaskEntities
import com.example.tudeeapp.presentation.screen.categories.expectedCategories
import com.example.tudeeapp.presentation.screen.categories.expectedTasks
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TaskServicesImplTest {
    private lateinit var taskServices: TaskServicesImpl
    private val taskDao: TaskDao = mockk(relaxed = true)
    private val categoryDao: CategoryDao = mockk(relaxed = true)
    private val dataConstant: DataConstant = mockk()

    @BeforeEach
    fun setUp() {
        taskServices = TaskServicesImpl(taskDao, categoryDao, dataConstant)
    }

    @Test
    fun `getCategories should return categories when getAll in CategoryDao called successfully`() =
        runTest {
            coEvery { categoryDao.getAllCategories() } returns flowOf(dummyCategoryEntities)

            val result = taskServices.getAllCategories().first()

            assertEquals(expectedCategories[0].title, result[0].title)
        }

    @Test
    fun `getCategories should throw exception when CategoryDao fails`() = runTest {
        coEvery { categoryDao.getAllCategories() } throws Exception()

        assertThrows<Exception> {
            taskServices.getAllCategories().toList()
        }
    }

    @Test
    fun `getTasks should return tasks when getAll in TaskDao called successfully`() = runTest {
        coEvery { taskDao.getAllTasks() } returns flowOf(dummyTaskEntities)

        val result = taskServices.getAllTasks().first()

        assertEquals(expectedTasks[0].title, result[0].title)
    }

    @Test
    fun `getTasks should throw exception when TaskDao fails`() = runTest {
        coEvery { taskDao.getAllTasks() } throws Exception()

        assertThrows<Exception> {
            taskServices.getAllTasks().toList()
        }
    }

    @Test
    fun `addTask() should add task successfully when valid task is provided`() = runTest {
        coEvery { taskDao.addOrUpdateTask(any()) } returns Unit

        taskServices.addTask(sampleTask)

        coVerify { taskDao.addOrUpdateTask(sampleTask.toTaskEntity()) }
    }

    @Test
    fun `addTask() should throw NoTaskAddedException when task addition fails`() = runTest {
        coEvery { taskDao.addOrUpdateTask(any()) } throws Exception("Database error")

        val exception = assertThrows<NoTaskAddedException> {
            taskServices.addTask(sampleTask)
        }

        assertThat(exception).isInstanceOf(NoTaskAddedException::class.java)
    }

    @Test
    fun `editTask() should edit task successfully when valid task is provided`() = runTest {
        coEvery { taskDao.addOrUpdateTask(any()) } returns Unit

        taskServices.editTask(sampleTask)

        coVerify { taskDao.addOrUpdateTask(sampleTask.toTaskEntity()) }
    }

    @Test
    fun `editTask() should throw NoTaskEditedException when task editing fails`() = runTest {
        coEvery { taskDao.addOrUpdateTask(any()) } throws Exception("Database error")

        val exception = assertThrows<NoTaskEditedException> {
            taskServices.editTask(sampleTask)
        }

        assertThat(exception).isInstanceOf(NoTaskEditedException::class.java)
    }

    @Test
    fun `addTask() should add task with empty title and description`() = runTest {
        val edgeCaseTask = sampleTask.copy(title = "", description = "")
        coEvery { taskDao.addOrUpdateTask(any()) } returns Unit

        taskServices.addTask(edgeCaseTask)

        coVerify { taskDao.addOrUpdateTask(edgeCaseTask.toTaskEntity()) }
    }

    @Test
    fun `addCategory() should add category successfully when valid category is provided`() =
        runTest {
            coEvery { categoryDao.insertOrUpdateCategory(any()) } returns Unit

            taskServices.addCategory(sampleCategory)

            coVerify { categoryDao.insertOrUpdateCategory(sampleCategory.toCategoryEntity()) }
        }

    @Test
    fun `addCategory() should throw AddCategoryException when category addition fails`() = runTest {
        coEvery { categoryDao.insertOrUpdateCategory(any()) } throws Exception("Database error")

        val exception = assertThrows<AddCategoryException> {
            taskServices.addCategory(sampleCategory)
        }

        assertThat(exception).isInstanceOf(AddCategoryException::class.java)
    }

    @Test
    fun `editCategory() should update category successfully when valid data is provided`() =
        runTest {

            coEvery { categoryDao.getCategoryById(sampleCategory.id) } returns flow {
                emit(sampleCategory.toCategoryEntity())
            }
            coEvery { categoryDao.insertOrUpdateCategory(any()) } returns Unit

            taskServices.editCategory(
                id = sampleCategory.id,
                title = sampleCategory.title,
                imageUri = sampleCategory.imageUri
            )

            coVerify {
                categoryDao.insertOrUpdateCategory(sampleCategory.toCategoryEntity())
            }
        }

    @Test
    fun `editCategory() should throw NoCategoryEditedException when update fails`() = runTest {
        coEvery { categoryDao.getCategoryById(sampleCategory.id) } returns flow {
            (sampleCategory.toCategoryEntity())
        }

        coEvery { categoryDao.insertOrUpdateCategory(any()) } throws Exception("Database error")

        val exception = assertThrows<NoCategoryEditedException> {
            taskServices.editCategory(
                id = sampleCategory.id,
                title = sampleCategory.title,
                imageUri = sampleCategory.imageUri
            )
        }

        assertThat(exception).isInstanceOf(NoCategoryEditedException::class.java)
    }

    @Test
    fun `deleteCategory() should delete category successfully when valid category is provided`() =
        runTest {
            coEvery { categoryDao.deleteCategory(sampleCategory.id) } returns Unit

            taskServices.deleteCategory(sampleCategory.id)

            coVerify { categoryDao.deleteCategory(sampleCategory.id) }
        }

    @Test
    fun `deleteCategory() should throw NoCategoryDeletedException when category deletion fails`() =
        runTest {
            coEvery { categoryDao.deleteCategory(sampleCategory.id) } throws Exception("Database error")

            val exception = assertThrows<NoCategoryDeletedException> {
                taskServices.deleteCategory(sampleCategory.id)
            }

            assertThat(exception).isInstanceOf(NoCategoryDeletedException::class.java)
        }

    private val sampleTask = Task(
        id = 1L,
        title = "Test Task",
        description = "Test Description",
        priority = TaskPriority.HIGH,
        status = TaskStatus.TO_DO,
        createdDate = LocalDateTime(
            year = 2002,
            monthNumber = 3,
            dayOfMonth = 1,
            hour = 13,
            minute = 50,
            second = 13,
            nanosecond = 1
        ),
        categoryId = 1L
    )

    private val sampleCategory = Category(
        id = 1L,
        title = "Sample Category",
        imageUri = "R.drawable.ic_sample_image",
        isPredefined = false,
        tasksCount = 0
    )
}
