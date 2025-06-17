package com.example.tudeeapp.presentation.bottomSheets.addEditTask

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.TaskPriorityUiState.Selected
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.AddEditStickButtons
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.AddEditTextFields
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.CategoryGrid
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.PriorityRow
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.components.TudeeDatePickerDialog
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskManagementBottomSheet(
    viewModel: TaskManagementViewModel = koinViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    TaskManagementBottomSheetContent(
        uiState = uiState,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onDateClicked = viewModel::onDateClicked,
        onPrioritySelected = viewModel::onPrioritySelected,
        onCategorySelected = viewModel::onCategorySelected,
        onActionButtonClicked = viewModel::onActionButtonClicked,
        onCancelClicked = viewModel::onCancelClicked,
    )

    if (uiState.isDatePickerVisible) {
        TudeeDatePickerDialog(
            initialDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            onDismiss = { viewModel.onDateClicked(false) },
            onSelectDate = {
                //Todo handle daialog
            }
        )
    }
}

@Composable
private fun TaskManagementBottomSheetContent(
    uiState: TaskManagementUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateClicked: (Boolean) -> Unit,
    onPrioritySelected: (TaskPriority) -> Unit,
    onCategorySelected: (Int) -> Unit,
    onActionButtonClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    TudeeBottomSheet(
        isVisible = uiState.isSheetVisible,
        title = if (uiState.isEditMode) stringResource(R.string.edit_task) else stringResource(R.string.add_task),
        onDismiss = {
            onCancelClicked
        },
        isScrollable = true,
        skipPartiallyExpanded = true,
        stickyBottomContent = {
            AddEditStickButtons(
                isEditMode = uiState.isEditMode,
                isActionButtonDisabled = uiState.isInitialState,
                onClickActionButton = onActionButtonClicked,
                onClickCancel = onCancelClicked,
            )
        },
    ) {
        AddEditTextFields(
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            onDateClicked = { onDateClicked(true) },
            title = uiState.title,
            description = uiState.description,
            date = uiState.selectedDate,
        )
        PriorityRow(
            modifier = Modifier.padding(16.dp),
            selectedPriority = uiState.selectedPriority,
            onPrioritySelected = onPrioritySelected
        )
        CategoryGrid(
            categories = uiState.categories,
            modifier = Modifier.padding(16.dp),
            onCategoryClick = onCategorySelected,
        )
    }
}

@PreviewLightDark
@Composable
private fun TaskManagementBottomSheetPreview() {
    val sampleCategories = listOf(
        CategoryUiState(image = R.drawable.ic_education, title = "Work"),
        CategoryUiState(image = R.drawable.ic_adoration, title = "Personal", isSelected = true),
        CategoryUiState(image = R.drawable.ic_gym, title = "Shopping"),
    )

    val mockUiState = TaskManagementUiState(
        title = "Mock Task Title",
        description = "Mock description for the task.",
        selectedDate = "2025-06-18",
        selectedPriority = Selected(TaskPriority.HIGH),
        categories = sampleCategories,
        selectedCategoryId = 1,
        isLoading = false,
    )

    BasePreview {

        TaskManagementBottomSheetContent(
            uiState = mockUiState,{},{},{},{},{},{},{},
        )
    }
}