package com.example.tudeeapp.presentation.screen.taskDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.LocalSnackBarState
import com.example.tudeeapp.presentation.common.components.PriorityButton
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.taskDetails.components.BoxTaskStatus
import com.example.tudeeapp.presentation.screen.taskDetails.components.CategoryIcon
import com.example.tudeeapp.presentation.screen.taskDetails.components.Divider1DPWithPadding
import com.example.tudeeapp.presentation.screen.taskDetails.components.StatusActionButtons
import com.example.tudeeapp.presentation.screen.taskDetails.components.TaskTitleAndDescription
import com.example.tudeeapp.presentation.utills.toPainter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = koinViewModel()
) {
    val taskDetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    TudeeBottomSheet(
        showSheet = true,
        title = stringResource(R.string.task_details),
        onDismiss =  viewModel::onDismiss ,
        stickyFooterContent = {
            taskDetailsUiState.taskUiState?.let { taskUiState ->
                if (taskUiState.status != TaskStatus.DONE)
                StickyFooterTaskDetails(
                    onStatusChange = { newStatus ->
                        viewModel.onEditTaskStatus(newStatus)
                    },
                    onEditTaskClick = viewModel::onEditTaskClick,
                    taskUiState = taskUiState,
                )
            }
        },
        content = {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    taskDetailsUiState.isLoading -> CircularProgressIndicator()
                    taskDetailsUiState.errorMessage.isNotEmpty() -> {
                        LocalSnackBarState.current.show(
                            taskDetailsUiState.errorMessage,
                            isSuccess = false
                        )
                    }

                    else -> {
                        val taskUiState = taskDetailsUiState.taskUiState
                        val categoryUiState = taskDetailsUiState.categoryUiState
                        if (taskUiState != null && categoryUiState != null) {
                            TaskDetailsContent(
                                taskUiState = taskUiState,
                                categoryUiState = categoryUiState,
                            )
                        }
                    }
                }
            }
        },
    )
}

@Composable
private fun StickyFooterTaskDetails(
    taskUiState: TaskUiState,
    onStatusChange: (TaskStatus) -> Unit,
    onEditTaskClick: () -> Unit
) {
    Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)) {
            StatusActionButtons(taskUiState, onEditTaskClick, onStatusChange)
    }
}

@Composable
private fun TaskDetailsContent(
    taskUiState: TaskUiState,
    categoryUiState: CategoryUiState,
) {

    val painter = toPainter(categoryUiState.isPredefined, categoryUiState.imageUri)
    val iconPriority = getPriorityIcon(taskUiState.priority)
    val backgroundPriorityColor = getPriorityBackgroundColor(taskUiState.priority)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceColors.surface)
            .padding()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        CategoryIcon(painter)
        TaskTitleAndDescription(taskUiState)
        Divider1DPWithPadding()
        Row {
            BoxTaskStatus(taskUiState)
            PriorityButton(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = taskUiState.priority.name.lowercase().replaceFirstChar { it.uppercase() },
                icon = iconPriority,
                backgroundColor = backgroundPriorityColor,
                onClick = {}
            )
        }
    }
}

@Composable
private fun getPriorityIcon(priority: TaskPriority) = when (priority) {
    TaskPriority.LOW -> painterResource(id = R.drawable.ic_trade_down)
    TaskPriority.MEDIUM -> painterResource(id = R.drawable.ic_alert)
    TaskPriority.HIGH -> painterResource(id = R.drawable.ic_flag)
}

@Composable
private fun getPriorityBackgroundColor(priority: TaskPriority) = when (priority) {
    TaskPriority.LOW -> Theme.colors.status.greenAccent
    TaskPriority.MEDIUM -> Theme.colors.status.yellowAccent
    TaskPriority.HIGH -> Theme.colors.status.pinkAccent
}

@Preview
@Composable
private fun TaskDetailsScreenPreview() {
    TaskDetailsScreen()
}