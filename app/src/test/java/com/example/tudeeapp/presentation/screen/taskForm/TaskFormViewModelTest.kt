package com.example.tudeeapp.presentation.screen.taskForm

import androidx.lifecycle.SavedStateHandle
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class TaskFormViewModelTest {
    private lateinit var viewModel: TaskFormViewModel
    private val taskServices: TaskServices = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TaskFormViewModel(taskServices, savedStateHandle)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllCategories() should update state with categories size when successful`() = runTest {
        val categories = testCategories

        val categoriesFlow = flowOf(categories)
        every { taskServices.getAllCategories() } returns categoriesFlow

        viewModel = TaskFormViewModel(taskServices, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.categories).hasSize(2)
    }

    @Test
    fun `getAllCategories() should handle exception and update error state`() = runTest {
        every { taskServices.getAllCategories() } throws Exception("Network error")

        viewModel = TaskFormViewModel(taskServices, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.error).isEqualTo("There was an error processing your request. Please try again later.")
    }

    @Test
    fun `onActionButtonClicked() should add task and verify task is saved`() = runTest {

        val testDate = LocalDate(2023, 1, 1)
        val testTitle = "Test Task"
        val testDescription = "Test Description"
        val testPriority = TaskPriorityUiState.HIGH
        val testCategoryId = 1L

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true
        val currentState = viewModel.state.value
        val updatedState = currentState.copy(
            title = testTitle,
            description = testDescription,
            selectedDate = testDate.toString(),
            selectedPriority = testPriority,
            selectedCategoryId = testCategoryId
        )

        (stateField.get(viewModel) as? MutableStateFlow<TaskFormUiState>)?.value =
            updatedState

        coEvery { taskServices.addTask(any()) } returns Unit

        viewModel.onActionButtonClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isTaskSaved).isTrue()
    }

    @Test
    fun `onActionButtonClicked() should edit task and verify task is saved`() = runTest {
        val taskFlow = MutableStateFlow(validTask)
        every { taskServices.getTaskById(validTaskId) } returns taskFlow
        every { taskServices.getAllCategories() } returns flowOf(emptyList())

        viewModel = TaskFormViewModel(taskServices, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onTitleChange("Updated Task")
        viewModel.onDescriptionChange("Updated Description")
        viewModel.onPrioritySelected(TaskPriorityUiState.HIGH)

        coEvery { taskServices.editTask(any()) } returns Unit

        viewModel.onActionButtonClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.isTaskSaved).isTrue()
    }

    @Test
    fun `onActionButtonClicked() should handle exception and update error state`() = runTest {
        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true
        val currentState = viewModel.state.value
        val updatedState = currentState.copy(
            title = "Test Task", description = "Test Description",
            selectedDate = LocalDate(2023, 1, 1).toString(),
            selectedPriority = TaskPriorityUiState.HIGH,
            selectedCategoryId = 1L
        )
        (stateField.get(viewModel) as MutableStateFlow<TaskFormUiState>).value = updatedState

        coEvery { taskServices.addTask(any()) } throws Exception("Database error")

        viewModel.onActionButtonClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.error).isEqualTo("There was an error processing your request. Please try again later.")
    }


    @Test
    fun `onDateClicked() should update isDatePickerVisible to true when true is passed`() =
        runTest {

            assertThat(viewModel.state.value.isDatePickerVisible).isFalse()

            viewModel.onDateClicked(true)
            testDispatcher.scheduler.advanceUntilIdle()

            assertThat(viewModel.state.value.isDatePickerVisible).isTrue()
        }

    @Test
    fun `onDateClicked() should update isDatePickerVisible to false when false is passed`() =
        runTest {

            viewModel.onDateClicked(true)
            testDispatcher.scheduler.advanceUntilIdle()
            assertThat(viewModel.state.value.isDatePickerVisible).isTrue()

            viewModel.onDateClicked(false)
            testDispatcher.scheduler.advanceUntilIdle()

            assertThat(viewModel.state.value.isDatePickerVisible).isFalse()
        }

    @Test
    fun `onDateSelected() should update selectedDate and hide date picker`() = runTest {
        val testDate = LocalDate(2025, 6, 22)
        val expectedDateString = testDate.toString()

        viewModel.onDateClicked(true)
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.state.value.isDatePickerVisible).isTrue()

        viewModel.onDateSelected(testDate)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.selectedDate).isEqualTo(expectedDateString)
        assertThat(viewModel.state.value.isDatePickerVisible).isFalse()
    }


    companion object {
        const val validTaskId = 123L
        val validTask = Task(
            id = validTaskId,
            title = "Original Task",
            description = "Original Description",
            priority = TaskPriority.LOW,
            status = TaskStatus.IN_PROGRESS,
            createdDate = LocalDate(2023, 1, 1),
            categoryId = 1L
        )

        val testCategories = listOf(
            Category(id = 1L, title = "Work", imageUrl = "work_icon"),
            Category(id = 2L, title = "Personal", imageUrl = "personal_icon")
        )
    }
}
