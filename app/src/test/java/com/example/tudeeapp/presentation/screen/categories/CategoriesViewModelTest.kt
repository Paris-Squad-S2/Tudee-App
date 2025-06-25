package com.example.tudeeapp.presentation.screen.categories

import com.example.tudeeapp.domain.TaskServices
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CategoryViewModelTest {
    private lateinit var taskServices: TaskServices
    private lateinit var viewModel: CategoriesViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        taskServices = mockk(relaxed = true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCategories should emits loading state when initial state is set `() = runTest {
        coEvery { taskServices.getAllCategories() } returns flowOf(expectedCategories)

        viewModel = CategoriesViewModel(taskServices)

        val state = viewModel.state.value
        assert(
            state == CategoriesScreenState(
                isLoading = true,
                categories = emptyList(),
                errorMessage = null
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCategories should emits categories when final state is set `() = runTest {
        coEvery { taskServices.getAllCategories() } returns flowOf(expectedCategories)
        coEvery { taskServices.getAllTasks() } returns flowOf(emptyList())

        viewModel = CategoriesViewModel(taskServices)
        advanceUntilIdle()

        assertThat(viewModel.state.value.categories).hasSize(2)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getCategories should emits error state when TaskServices fail`() = runTest {
        coEvery { taskServices.getAllCategories() } throws Exception("Network error")

        viewModel = CategoriesViewModel(taskServices)
        advanceUntilIdle()

        val state = viewModel.state.value
        assert(
            state == CategoriesScreenState(
                isLoading = false,
                categories = emptyList(),
                errorMessage = "There was an error processing your request. Please try again later."
            )
        )
    }

}