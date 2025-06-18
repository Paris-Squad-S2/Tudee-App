package com.example.tudeeapp.presentation.screen.taskManagement

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.screen.taskManagement.components.AddEditStickButtons
import com.example.tudeeapp.presentation.screen.taskManagement.components.TaskManagementTextFields
import com.example.tudeeapp.presentation.screen.taskManagement.components.CategoryGrid
import com.example.tudeeapp.presentation.screen.taskManagement.components.PriorityRow
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.components.TudeeDatePickerDialog
import com.example.tudeeapp.presentation.navigation.LocalNavController
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskManagementBottomSheet(
    viewModel: TaskManagementViewModel = koinViewModel(),
) {
    val navController = LocalNavController.current

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    TaskManagementBottomSheetContent(
        uiState = uiState,
        viewModel = viewModel,
        onCancelClicked = { navController.popBackStack() }
    )

    if (uiState.isDatePickerVisible) {
        TudeeDatePickerDialog(
            initialDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            onDismiss = { viewModel.onDateClicked(false) },
            onSelectDate = { viewModel.onDateSelected(it) }
        )
    }
}

@Composable
private fun TaskManagementBottomSheetContent(
    uiState: TaskManagementUiState,
    viewModel: TaskManagementViewModel,
    onCancelClicked: () -> Unit
) {
    TudeeBottomSheet(
        isVisible = true,
        title = if (uiState.isEditMode) stringResource(R.string.edit_task) else stringResource(R.string.add_task),
        onDismiss =onCancelClicked,
        isScrollable = true,
        skipPartiallyExpanded = true,
        stickyBottomContent = {
            AddEditStickButtons(
                isEditMode = uiState.isEditMode,
                isActionButtonDisabled = uiState.isInitialState,
                onClickActionButton = viewModel::onActionButtonClicked,
                onClickCancel = onCancelClicked,
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