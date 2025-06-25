package com.example.tudeeapp.presentation.screen.taskManagement

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.components.TudeeDatePickerDialog
import com.example.tudeeapp.presentation.LocalSnackBarState
import com.example.tudeeapp.presentation.screen.taskManagement.components.CategoryGrid
import com.example.tudeeapp.presentation.screen.taskManagement.components.PriorityRow
import com.example.tudeeapp.presentation.screen.taskManagement.components.TaskManagementButtons
import com.example.tudeeapp.presentation.screen.taskManagement.components.TaskManagementTextFields
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskManagementBottomSheet(
    viewModel: TaskManagementViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackBarState.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val successMessage = stringResource(R.string.task_saved_successfully)

    TaskManagementBottomSheetContent(
        uiState = uiState,
        viewModel = viewModel,
        onCancelClicked = { viewModel.popBackStack() }
    )

    if (uiState.isDatePickerVisible) {
        TudeeDatePickerDialog(
            initialDate = LocalDate.parse(uiState.selectedDate),
            onDismiss = { viewModel.onDateClicked(false) },
            onSelectDate = { viewModel.onDateSelected(it) }
        )
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.show(message = it, isSuccess = false)
        }
    }

    LaunchedEffect(uiState.isTaskSaved) {
        if (uiState.isTaskSaved) {
            snackbarHostState.show(message = successMessage, isSuccess = true)
            viewModel.popBackStack()
        }
    }
}

@Composable
private fun TaskManagementBottomSheetContent(
    uiState: TaskManagementUiState,
    viewModel: TaskManagementViewModel,
    onCancelClicked: () -> Unit,
) {
    TudeeBottomSheet(
        showBottomSheet = true,
        isDraggable = true,
        title = if (uiState.isEditMode) stringResource(R.string.edit_task) else stringResource(R.string.add_task),
        onDismiss = onCancelClicked,
        isScrollable = true,
        skipPartiallyExpanded = false,
        stickyBottomContent = {
            TaskManagementButtons(
                isEditMode = uiState.isEditMode,
                isActionButtonDisabled = uiState.isInitialState,
                onClickActionButton = {
                    viewModel.onActionButtonClicked()
                },
                onClickCancel = onCancelClicked,
                isLoading = uiState.isLoading,
            )
        },
    ) {
        TaskManagementTextFields(
            onTitleChange = viewModel::onTitleChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onDateClicked = { viewModel.onDateClicked(true) },
            title = uiState.title,
            description = uiState.description,
            date = uiState.selectedDate,
        )
        PriorityRow(
            modifier = Modifier.padding(16.dp),
            selectedPriority = uiState.selectedPriority,
            onPrioritySelected = viewModel::onPrioritySelected
        )
        CategoryGrid(
            categories = uiState.categories,
            modifier = Modifier.padding(16.dp),
            onCategoryClick = viewModel::onCategorySelected,
        )
    }
}