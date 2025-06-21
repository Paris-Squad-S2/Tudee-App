package com.example.tudeeapp.presentation.screen.categoryDetails

import androidx.lifecycle.SavedStateHandle
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.navigation.Screens
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import androidx.navigation.toRoute
import io.mockk.mockkStatic

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryDetailsViewModelTest {

    private lateinit var viewModel: CategoryDetailsViewModel
    private var taskServices: TaskServices = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private var categoryId: Long = 1L

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        taskServices = mockk(relaxed = true)

        categoryId = 1L

        mockkStatic("androidx.navigation.SavedStateHandleKt")

        every {
            savedStateHandle.toRoute<Screens.CategoryDetails>()
        } returns Screens.CategoryDetails(categoryId = categoryId)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCategory() should update uiState with category and tasks`() = runTest {
        val category = Category(id = 1, title = "coding", imageUrl = "ic_coding")
        val tasks = listOf(
            Task(1, "Task 1", "desc", TaskPriority.LOW, TaskStatus.TO_DO, LocalDate(2023, 1, 1), 1),
            Task(2, "Task 2", "desc", TaskPriority.HIGH, TaskStatus.IN_PROGRESS, LocalDate(2023, 1, 2), 1),
        )

        coEvery { taskServices.getCategoryById(categoryId) } returns flowOf(category)
        coEvery { taskServices.getAllTasks() } returns flowOf(tasks)

        viewModel = CategoryDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.taskUiState).hasSize(2)
        assertThat(state.categoryUiState?.title).isEqualTo("coding")
    }

    @Test
    fun `deleteTask() should remove task and update state`() = runTest {
        val category = Category(1, "book", "ic_book")
        val tasks = listOf(
            Task(1, "Task 1", "desc", TaskPriority.MEDIUM, TaskStatus.TO_DO, LocalDate(2023, 1, 1), 1)
        )

        coEvery { taskServices.getCategoryById(categoryId) } returns flowOf(category)
        coEvery { taskServices.getAllTasks() } returns flowOf(tasks)
        coEvery { taskServices.deleteTask(1) } returns Unit

        viewModel = CategoryDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.deleteTask(1)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.errorMessage).isEmpty()
    }

    @Test
    fun `updateUiStateWithFilters() should refresh tasks correctly`() = runTest {
        val category = Category(1, "the chance", "ic_the_chance")
        val tasks = listOf(
            Task(1, "Task 1", "desc", TaskPriority.LOW, TaskStatus.TO_DO, LocalDate(2023, 1, 1), 1),
            Task(2, "Task 2", "desc", TaskPriority.MEDIUM, TaskStatus.IN_PROGRESS, LocalDate(2023, 1, 2), 2) // not same category
        )

        coEvery { taskServices.getCategoryById(categoryId) } returns flowOf(category)
        coEvery { taskServices.getAllTasks() } returns flowOf(tasks)

        viewModel = CategoryDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.updateUiStateWithFilters()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.taskUiState).hasSize(1)
    }

    @Test
    fun `setStatus() should update stateFilter`() = runTest {
        coEvery { taskServices.getCategoryById(categoryId) } returns flowOf(
            Category(1, "the chance", "ic_the_chance")
        )
        coEvery { taskServices.getAllTasks() } returns flowOf(emptyList())

        viewModel = CategoryDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.setStatus(TaskStatus.DONE)

        assertThat(viewModel.stateFilter.value).isEqualTo(TaskStatus.DONE)
    }

    @Test
    fun `loadCategory() should update errorMessage on failure`() = runTest {
        coEvery { taskServices.getCategoryById(categoryId) } throws RuntimeException("Something went wrong")
        coEvery { taskServices.getAllTasks() } returns flowOf(emptyList())

        viewModel = CategoryDetailsViewModel(savedStateHandle, taskServices)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.errorMessage).contains("Something went wrong")
    }
}
