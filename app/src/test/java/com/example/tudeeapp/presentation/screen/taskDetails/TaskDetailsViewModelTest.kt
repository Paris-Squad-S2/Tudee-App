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
import com.example.tudeeapp.presentation.navigation.Destinations
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
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
        every { savedStateHandle.toRoute<Destinations.TaskDetails>() } returns Destinations.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.taskUiState).isNotNull()
    }

    @Test
    fun `should return null data when getCategoryById is invalid`() = runTest {
        every { taskServices.getTaskById(any()) } returns flowOf(testTask)
        every { savedStateHandle.toRoute<Destinations.TaskDetails>() } returns Destinations.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.taskUiState).isNull()
    }

    @Test
    fun `should update uiState with errorMessage when task id is invalid`() = runTest {

        every { taskServices.getTaskById(any()) } returns flow { throw TaskException("Invalid task id") }
        every { savedStateHandle.toRoute<Destinations.TaskDetails>() } returns Destinations.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isEqualTo("Task , Invalid task id")
    }

    @Test
    fun `should update uiState with errorMessage when category id is invalid`() = runTest {

        every { taskServices.getTaskById(any()) } returns flowOf(testTask)
        every { taskServices.getCategoryById(any()) } returns flow { throw CategoryException("Invalid category id") }
        every { savedStateHandle.toRoute<Destinations.TaskDetails>() } returns Destinations.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isEqualTo("Category , Invalid category id")
    }

    @Test
    fun `onEditTaskStatus updates status when successful`() = runTest {

        every { taskServices.getTaskById(any()) } returns flowOf(testTask)
        every { taskServices.getCategoryById(any()) } returns flowOf(testCategory)
        every { savedStateHandle.toRoute<Destinations.TaskDetails>() } returns Destinations.TaskDetails(1L)
        coEvery { taskServices.editTask(any()) } returns Unit

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onEditTaskStatus(TaskStatus.DONE)
        testDispatcher.scheduler.advanceUntilIdle()


        coVerify(exactly = 1) { taskServices.editTask(testTask.copy(status = TaskStatus.DONE)) }
        assertThat(viewModel.uiState.value.taskUiState?.status).isEqualTo(TaskStatus.DONE)
    }

    @Test
    fun `onEditTaskStatus sets error message when editTask fails`() = runTest {

        every { taskServices.getTaskById(any()) } returns flowOf(testTask)
        every { taskServices.getCategoryById(any()) } returns flowOf(testCategory)
        every { savedStateHandle.toRoute<Destinations.TaskDetails>() } returns Destinations.TaskDetails(1L)

        val errorMessage = "Update failed"
        coEvery { taskServices.editTask(any()) } throws TaskException(errorMessage)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onEditTaskStatus(TaskStatus.DONE)
        testDispatcher.scheduler.advanceUntilIdle()


        assertThat(viewModel.uiState.value.errorMessage).isEqualTo("Task , Update failed")
    }

    @Test
    fun `onEditTaskStatus does nothing when taskUiState is null`() = runTest {
        every { taskServices.getTaskById(any()) } returns flow { throw TaskException("Not found") }
        every { savedStateHandle.toRoute<Destinations.TaskDetails>() } returns Destinations.TaskDetails(1L)

        viewModel = TaskDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onEditTaskStatus(TaskStatus.DONE)

        coVerify(exactly = 0) { taskServices.editTask(any()) }
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
