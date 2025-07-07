package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class CategoryFormViewModelTest {

    private lateinit var viewModel: CategoryFormViewModel
    private val taskServices: TaskServices = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CategoryFormViewModel(taskServices, savedStateHandle)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSubmit() should call addCategory when no categoryId passed`() = runTest {

        coEvery { taskServices.addCategory(any()) } returns Unit

        viewModel.onSubmit()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.successMessage).isEqualTo(R.string.category_added_successfully)
    }

    @Test
    fun `onSubmit() should update errorMessage when addCategory throws exception`() = runTest {


        coEvery { taskServices.addCategory(any()) } throws Exception("Add failed")

        viewModel.onSubmit()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isEqualTo(R.string.failed_to_add_category)
    }

    @Test
    fun `onSubmit() should call editCategory when categoryId passed`() = runTest {

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true

        val mutableStateFlow = stateField.get(viewModel) as MutableStateFlow<CategoryFormUIState>
        mutableStateFlow.value = CategoryFormUIState(
            categoryId = testCategory.id,
            categoryName = testCategory.title,
            imageUri = Uri.EMPTY
        )

        coEvery { taskServices.editCategory(any(), any(), any()) } returns Unit

        viewModel.onSubmit()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.successMessage).isEqualTo(R.string.edited_category_successfully)
    }

    @Test
    fun `onSubmit() should update errorMessage when editCategory throws exception`() = runTest {

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true
        val mutableStateFlow = stateField.get(viewModel) as MutableStateFlow<CategoryFormUIState>
        mutableStateFlow.value = mutableStateFlow.value.copy(
            categoryId = testCategory.id,
            imageUri = Uri.EMPTY
        )

        coEvery { taskServices.editCategory(any(), any(), any()) } throws Exception("Edit failed")

        viewModel.onSubmit()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isEqualTo(R.string.failed_to_edit_category)
    }

    @Test
    fun `deleteCategory() should invoke onSuccess when deletion succeeds`() = runTest {

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true
        val mutableStateFlow = stateField.get(viewModel) as MutableStateFlow<CategoryFormUIState>
        mutableStateFlow.value = CategoryFormUIState(
            categoryId = testCategory.id,
        )

        coEvery { taskServices.deleteCategory(testCategory.id) } returns Unit

        viewModel.onDelete()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.successMessage).isEqualTo(R.string.category_deleted_successfully)
    }

    @Test
    fun `deleteCategory() should update errorMessage when deletion fails`() = runTest {

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true
        val mutableStateFlow = stateField.get(viewModel) as MutableStateFlow<CategoryFormUIState>
        mutableStateFlow.value = CategoryFormUIState(
            categoryId = testCategory.id
        )

        coEvery { taskServices.deleteCategory(testCategory.id) } throws Exception("Delete error")

        viewModel.onDelete()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isEqualTo(R.string.failed_to_delete_category)
    }

    companion object {
        val testCategory = Category(
            id = 1L,
            title = "Test Category",
            imageUri = "R.drawable.ic_test_category",
            isPredefined = false,
            tasksCount = 0
        )
    }

}