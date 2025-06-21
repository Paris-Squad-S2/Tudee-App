package com.example.tudeeapp.presentation.screen.taskDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.CategoryException
import com.example.tudeeapp.domain.exception.TaskException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Screens
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailsViewModelTest {
    private lateinit var viewModel: TaskDetailsViewModel
    private val taskServices: TaskServices = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should return task details successfully when loading task details`() = runTest {

        every { taskServices.getTaskById(any()) } returns flowOf(testTask)
        every { taskServices.getCategoryById(any()) } returns flowOf(testCategory)
        every { savedStateHandle.toRoute<Screens.TaskDetails>() } returns Screens.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.taskUiState).isNotNull()
    }

    @Test
    fun `should return null data when getCategoryById null`() = runTest {

        every { taskServices.getTaskById(any()) } returns  flowOf(testTask)
        every { savedStateHandle.toRoute<Screens.TaskDetails>() } returns Screens.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.taskUiState).isNull()
    }

    @Test
    fun `should update uiState with errorMessage when task id is invalid`() = runTest {

        every { taskServices.getTaskById(any()) } returns flow {
            throw TaskException("Invalid task id")
        }

        every { savedStateHandle.toRoute<Screens.TaskDetails>() } returns Screens.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isEqualTo("Task , Invalid task id")
    }

    @Test
    fun `should update uiState with errorMessage when category id is invalid`() = runTest {

        every { taskServices.getTaskById(any()) } returns flowOf(testTask)
        every { taskServices.getCategoryById(any()) } returns flow {
            throw CategoryException("Invalid category id")
        }
        every { savedStateHandle.toRoute<Screens.TaskDetails>() } returns Screens.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isEqualTo("Category , Invalid category id")
    }

    @Test
    fun `onEditTaskStatus updates status when successful`() = runTest {
        every { taskServices.getTaskById(any()) } returns flowOf(testTask)
        every { taskServices.getCategoryById(any()) } returns flowOf(testCategory)
        every { savedStateHandle.toRoute<Screens.TaskDetails>() } returns Screens.TaskDetails(1L)

        coEvery { taskServices.editTask(any()) } returns Unit

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle() // Wait for initial load

        viewModel.onEditTaskStatus(TaskStatus.DONE)
        testDispatcher.scheduler.advanceUntilIdle() // Process coroutine


        coVerify(exactly = 1) { taskServices.editTask(testTask.copy(status = TaskStatus.DONE)) }
        assertThat(viewModel.uiState.value.taskUiState?.status).isEqualTo(TaskStatus.DONE)
    }


    private val testTask = Task(
        id = 1L,
        title = "Test Task",
        description = "Test Description",
        priority = TaskPriority.HIGH,
        status = TaskStatus.TO_DO,
        createdDate = LocalDate(2023, 1, 1),
        categoryId = 2L
    )

    private val testCategory = Category(
        id = 2L,
        title = "Work",
        imageUrl = "work_icon"
    )
}
