package com.example.tudeeapp.presentation.screen.taskForm

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
import com.example.tudeeapp.presentation.screen.taskForm.components.TaskFormTextFields
import com.example.tudeeapp.presentation.screen.taskForm.components.CategoryGrid
import com.example.tudeeapp.presentation.screen.taskForm.components.PriorityRow
import com.example.tudeeapp.presentation.screen.taskForm.components.TaskManagementButtons
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskFormBottomSheet(
    viewModel: TaskFormViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackBarState.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val successMessage = stringResource(R.string.task_saved_successfully)

    TaskManagementBottomSheetContent(
        uiState = uiState,
        interactionListener  = viewModel,
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
    uiState: TaskFormUiState,
    interactionListener: InteractionListener,
) {
    TudeeBottomSheet(
        isVisible = true,
        stopBarrierDismiss = true,
        title = if (uiState.isEditMode) stringResource(R.string.edit_task) else stringResource(R.string.add_task),
        onDismiss = interactionListener::popBackStack,
        isScrollable = true,
        skipPartiallyExpanded = true,
        stickyBottomContent = {
            TaskManagementButtons(
                isEditMode = uiState.isEditMode,
                isActionButtonDisabled = uiState.isInitialState,
                onClickActionButton = interactionListener::onActionButtonClicked,
                onClickCancel = interactionListener::popBackStack,
                isLoading = uiState.isLoading,
            )
        },
    ) {
        TaskFormTextFields(
            onTitleChange = interactionListener::onTitleChange,
            onDescriptionChange = interactionListener::onDescriptionChange,
            onDateClicked = { interactionListener.onDateClicked(true) },
            title = uiState.title,
            description = uiState.description,
            date = uiState.selectedDate,
        )
        PriorityRow(
            modifier = Modifier.padding(16.dp),
            selectedPriority = uiState.selectedPriority,
            onPrioritySelected = interactionListener::onPrioritySelected
        )
        CategoryGrid(
            categories = uiState.categories,
            modifier = Modifier.padding(16.dp),
            onCategoryClick = interactionListener::onCategorySelected,
        )
    }
}