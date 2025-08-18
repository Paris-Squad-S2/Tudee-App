/*
package com.example.tudeeapp.presentation.screen

import com.example.tudeeapp.MainViewModel
import com.example.tudeeapp.domain.TaskServices
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel
    private val taskServices: TaskServices = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `loadPredefinedCategories() should call service successfully`() = runTest {
        // Given
        coEvery { taskServices.loadPredefinedCategories() } returns Unit

        // When
        viewModel = MainViewModel(taskServices)
        viewModel.loadPredefinedCategories()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { taskServices.loadPredefinedCategories() }
    }

}*/
