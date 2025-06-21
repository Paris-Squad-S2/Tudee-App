package com.example.tudeeapp.presentation.screen.categoriesForm

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.example.tudeeapp.R
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category
import org.junit.jupiter.api.Test
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.jvm.java


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
    fun `submitCategory() should call addCategory when no categoryId passed`() = runTest {
        viewModel.updateCategoryName(testCategory.title)

        coEvery { taskServices.addCategory(any()) } returns Unit

        viewModel.submitCategory()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.successMessage).isEqualTo(R.string.category_added_successfully)
    }

    @Test
    fun `submitCategory() should update errorMessage when addCategory throws exception`() = runTest {

        viewModel.updateCategoryName(testCategory.title)

        coEvery { taskServices.addCategory(any()) } throws Exception("Add failed")

        viewModel.submitCategory()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.errorMessage).isEqualTo(R.string.failed_to_add_category)
    }

    @Test
    fun `submitCategory() should call editCategory when categoryId passed`() = runTest {

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true

        val mutableStateFlow = stateField.get(viewModel) as MutableStateFlow<CategoryFormUIState>
        mutableStateFlow.value = CategoryFormUIState(
            categoryId = testCategory.id,
            categoryName = testCategory.title,
            imageUri = Uri.EMPTY
        )

        coEvery { taskServices.editCategory(any(), any(), any()) } returns Unit

        viewModel.submitCategory()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.successMessage).isEqualTo(R.string.edited_category_successfully)
    }

    @Test
    fun `submitCategory() should update errorMessage when editCategory throws exception`() = runTest {

        viewModel.updateCategoryName(testCategory.title)

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true
        val mutableStateFlow = stateField.get(viewModel) as MutableStateFlow<CategoryFormUIState>
        mutableStateFlow.value = mutableStateFlow.value.copy(
            categoryId = testCategory.id,
            imageUri = Uri.EMPTY
        )

        coEvery { taskServices.editCategory(any(), any(), any()) } throws Exception("Edit failed")

        viewModel.submitCategory()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.errorMessage).isEqualTo(R.string.failed_to_edit_category)
    }

    @Test
    fun `deleteCategory() should invoke onSuccess when deletion succeeds`() = runTest {
        var successCalled = false

        val stateField = viewModel::class.java.getDeclaredField("_state")
        stateField.isAccessible = true
        val mutableStateFlow = stateField.get(viewModel) as MutableStateFlow<CategoryFormUIState>
        mutableStateFlow.value = CategoryFormUIState(
            categoryId = testCategory.id
        )

        coEvery { taskServices.deleteCategory(testCategory.id) } returns Unit

        viewModel.deleteCategory(onSuccess = { successCalled = true })
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(successCalled).isTrue()
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

        viewModel.deleteCategory(onSuccess = {})
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.errorMessage).isEqualTo(R.string.failed_to_delete_category)
    }

    companion object {
        val testCategory = Category(
            id = 1L,
            title = "Test Category",
            imageUrl = "R.drawable.ic_test_category",
            isPredefined = false,
            tasksCount = 0
        )
    }

}