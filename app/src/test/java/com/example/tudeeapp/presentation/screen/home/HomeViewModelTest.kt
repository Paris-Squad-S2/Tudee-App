package com.example.tudeeapp.presentation.screen.home

import app.cash.turbine.test
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.exception.CategoryNotFoundException
import com.example.tudeeapp.domain.exception.TasksNotFoundException
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.screen.home.utils.getToday
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private val taskServices: TaskServices = mockk(relaxed = true)
    private val appPreferences: AppPreferences = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        coEvery { taskServices.getAllTasks() } returns flowOf(emptyList())
        coEvery { taskServices.getCategoryById(any()) } returns flowOf(createCategory())
        every { appPreferences.isDarkTheme() } returns false


        homeViewModel = HomeViewModel(taskServices, appPreferences)


        runTest { advanceUntilIdle() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `initial state when tasks and dark mode in default values`() {
        runTest(dispatcher) {

            homeViewModel = HomeViewModel(taskServices, appPreferences)

            homeViewModel.uiState.test {
                val initialState = awaitItem()

                assertThat(initialState.toDoTasks).isEmpty()
                assertThat(initialState.inProgressTasks).isEmpty()
                assertThat(initialState.doneTasks).isEmpty()
                assertThat(initialState.isTasksEmpty).isTrue()
                assertFalse(initialState.isLoading)
                assertFalse(initialState.isSuccess)
                assertThat(initialState.error).isNull()
                cancelAndConsumeRemainingEvents()
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onToggledAction should update isDarkMode in state and call setDarkTheme in appPreferences`() {

        runTest(dispatcher) {

            coEvery { appPreferences.setDarkTheme(any()) } returns Unit

            homeViewModel.uiState.test {

                val initialStateAfterSetup = awaitItem()
                assertThat(initialStateAfterSetup.isDarkMode).isFalse()

                val newDarkModeValue = !initialStateAfterSetup.isDarkMode

                homeViewModel.onToggledAction(newDarkModeValue)
                advanceUntilIdle()

                val stateAfterToggle = awaitItem()
                verify(exactly = 1) { appPreferences.setDarkTheme(newDarkModeValue) }
                Assertions.assertEquals(newDarkModeValue, stateAfterToggle.isDarkMode)

                cancelAndConsumeRemainingEvents()
            }

        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTasksIcons should load today's tasks and update state accordingly`() {
        runTest(dispatcher) {

            coEvery { taskServices.getAllTasks() } returns flowOf(listOf(createTask()))
            coEvery { taskServices.getAllTasks() } returns flowOf(listOf(createTask(id = 5, categoryId = 5)))

            coEvery { taskServices.getCategoryById(any()) } returns flowOf(createCategory(id = 5))

            homeViewModel = HomeViewModel(taskServices, appPreferences)

            homeViewModel.uiState.test {

                val initialState = awaitItem()
                assertThat(initialState.toDoTasks).isEmpty()

                homeViewModel.getTasksIcons()
                advanceUntilIdle()

                val state = awaitItem()
                assertThat(state.toDoTasks).isNotEmpty()
                assertThat(state.inProgressTasks).isEmpty()
                assertThat(state.doneTasks).isEmpty()
                assertThat(state.isTasksEmpty).isFalse()
                Assertions.assertTrue(state.isSuccess)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTasksIcons should throw NoTasksFoundException when fetching tasks fails`() {
        runTest(dispatcher) {

            coEvery { taskServices.getAllTasks() } throws TasksNotFoundException()

            coEvery { taskServices.getCategoryById(any()) } returns flowOf(createCategory(id = 5))

            homeViewModel = HomeViewModel(taskServices, appPreferences)

            homeViewModel.uiState.test {

                val initialState = awaitItem()
                assertThat(initialState.toDoTasks).isEmpty()

                homeViewModel.getTasksIcons()
                advanceUntilIdle()

                val state = awaitItem()
                assertThat(state.error).isEqualTo(TasksNotFoundException().message)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTasksIcons should throw CategoryNotFoundException when fetching category fails`() {
        runTest(dispatcher) {

            coEvery { taskServices.getAllTasks() } returns flowOf(listOf(createTask()))
            coEvery { taskServices.getAllTasks() } returns flowOf(listOf(createTask(id = 5, categoryId = 5)))

            coEvery { taskServices.getCategoryById(any()) } throws CategoryNotFoundException()

            homeViewModel = HomeViewModel(taskServices, appPreferences)

            homeViewModel.uiState.test {

                val initialState = awaitItem()
                assertThat(initialState.toDoTasks).isEmpty()
                assertThat(initialState.error).isNull()

                homeViewModel.getTasksIcons()
                advanceUntilIdle()

                val state = awaitItem()
                assertThat(state.error).isEqualTo(CategoryNotFoundException().message)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTasks should load today's tasks and update state accordingly`() {
        runTest(dispatcher) {

            every { taskServices.getAllTasks() } returns flowOf(listOf(createTask(status = TaskStatus.TO_DO)))

            homeViewModel = HomeViewModel(taskServices, appPreferences)

            homeViewModel.uiState.test {

                val initialState = awaitItem()
                assertThat(initialState.toDoTasks).isEmpty()

                homeViewModel.getTasks()
                advanceUntilIdle()

                val state = awaitItem()
                assertThat(state.toDoTasks).isNotEmpty()
                Assertions.assertTrue(state.isSuccess)
                cancelAndConsumeRemainingEvents()
            }

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTasks should throw TasksNotFoundException( when fetching tasks fails`() {
        runTest(dispatcher) {

            every { taskServices.getAllTasks() } throws TasksNotFoundException()

            homeViewModel = HomeViewModel(taskServices, appPreferences)

            homeViewModel.uiState.test {

                val initialState = awaitItem()
                assertThat(initialState.error).isNull()

                homeViewModel.getTasks()
                advanceUntilIdle()

                val state = awaitItem()
                assertThat(state.error).isEqualTo(TasksNotFoundException().message)
                cancelAndConsumeRemainingEvents()
            }

        }
    }
}


private fun createTask(
    id: Long = 1,
    title: String = "Test Task",
    description: String = "Description",
    priority: TaskPriority = TaskPriority.LOW,
    status: TaskStatus = TaskStatus.TO_DO,
    categoryId: Long = 2,
    createdDate: LocalDate = getToday().date
): Task {
    return Task(id, title, description, priority, status, createdDate, categoryId)
}

private fun createCategory(
    id: Long = 2,
    imageUrl: String = "http://example.com/icon.png",
    isPredefined: Boolean = true
): Category {
    return Category(
        id = id,
        title = "Category Name",
        imageUrl = imageUrl,
        isPredefined = isPredefined
    )
}
