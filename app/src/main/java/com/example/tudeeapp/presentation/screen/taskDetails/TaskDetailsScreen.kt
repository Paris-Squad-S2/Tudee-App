package com.example.tudeeapp.presentation.screen.taskDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.PriorityButton
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.navigation.LocalNavController
import com.example.tudeeapp.presentation.navigation.LocalSnackBarState
import com.example.tudeeapp.presentation.navigation.Screens
import com.example.tudeeapp.presentation.screen.taskDetails.state.CategoryUiState
import com.example.tudeeapp.presentation.screen.taskDetails.state.TaskUiState
import com.example.tudeeapp.presentation.utills.toPainter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = koinViewModel()
) {
    val navController = LocalNavController.current
    val taskDetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isSheetOpen by remember { mutableStateOf(true) }

    TudeeBottomSheet(
        isVisible = true,
        title = stringResource(R.string.task_details),
        isScrollable = false,
        skipPartiallyExpanded = false,
        onDismiss = { navController.popBackStack() },
        content = {

            when {
                taskDetailsUiState.isLoading -> LoadingView()
                taskDetailsUiState.errorMessage.isNotEmpty() -> {
                    LocalSnackBarState.current.show(
                        taskDetailsUiState.errorMessage,
                        isSuccess = false
                    )
                    isSheetOpen = false
                }

                else -> {
                    val taskUiState = taskDetailsUiState.taskUiState
                    val categoryUiState = taskDetailsUiState.categoryUiState
                    if (taskUiState != null && categoryUiState != null) {
                        TaskDetailsContent(
                            taskUiState = taskUiState,
                            categoryUiState = categoryUiState,
                            onStatusChange = { newStatus -> viewModel.onEditTaskStatus(newStatus) },
                            onEditTaskClick = {
                                navController.navigate(
                                    Screens.TaskManagement(
                                        taskUiState.id
                                    )
                                )
                            }
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun LoadingView() {
    CircularProgressIndicator()
}

@Composable
private fun TaskDetailsContent(
    taskUiState: TaskUiState,
    categoryUiState: CategoryUiState,
    onStatusChange: (TaskStatus) -> Unit,
    onEditTaskClick: () -> Unit
) {

    val painter = toPainter(categoryUiState.isPredefined, categoryUiState.imageUrl)
    val iconPriority = getPriorityIcon(taskUiState.priority)
    val backgroundPriorityColor = getPriorityBackgroundColor(taskUiState.priority)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceColors.surface)
            .padding()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
    ) {
        CategoryIcon(painter)
        TaskTexts(taskUiState)
        HorizontalLine1Dp()
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
        if (taskUiState.status != TaskStatus.DONE)
            StatusActionButtons(taskUiState, onEditTaskClick, onStatusChange)
    }
}

@Composable
private fun HorizontalLine1Dp() {
    Spacer(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .height(1.dp)
            .background(Theme.colors.stroke)
    )
}

@Composable
private fun CategoryIcon(painter: Painter) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .padding(top = 12.dp)
            .background(color = Theme.colors.surfaceColors.surfaceHigh, shape = CircleShape)
    ) {
        Icon(
            painter = painter,
            contentDescription = "task category icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun TaskTexts(taskUiState: TaskUiState) {
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = taskUiState.title,
        style = Theme.textStyle.title.medium,
        color = Theme.colors.text.title
    )
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = taskUiState.description,
        style = Theme.textStyle.body.medium,
        color = Theme.colors.text.body
    )
}

@Composable
private fun StatusActionButtons(
    taskUiState: TaskUiState,
    onEditTaskClick: () -> Unit,
    onStatusChange: (TaskStatus) -> Unit
) {
    Row(modifier = Modifier.padding(top = 24.dp)) {
        TudeeButton(
            onClick = { onEditTaskClick() },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_pencil_edit_24),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
            variant = ButtonVariant.OutlinedButton,
        )
        Spacer(Modifier.width(4.dp))
        TudeeButton(
            modifier = Modifier.weight(1f),
            onClick = {
                val newStatus = if (taskUiState.status == TaskStatus.IN_PROGRESS) {
                    TaskStatus.DONE
                } else {
                    TaskStatus.IN_PROGRESS
                }
                onStatusChange(newStatus)
            },
            text = if (taskUiState.status == TaskStatus.IN_PROGRESS) {
                stringResource(R.string.move_to_done)
            } else {
                stringResource(R.string.move_to_in_progress)
            },
            variant = ButtonVariant.OutlinedButton,
        )
    }
}


@Composable
private fun BoxTaskStatus(taskUiState: TaskUiState) {

    val textStatusColor = when (taskUiState.status) {
        TaskStatus.DONE -> Theme.colors.status.yellowAccent
        TaskStatus.TO_DO -> Theme.colors.status.greenAccent
        TaskStatus.IN_PROGRESS -> Theme.colors.status.purpleVariant
    }

    val backgroundStatusColor = when (taskUiState.status) {
        TaskStatus.DONE -> Theme.colors.status.yellowVariant
        TaskStatus.TO_DO -> Theme.colors.status.greenVariant
        TaskStatus.IN_PROGRESS -> Theme.colors.status.purpleVariant
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundStatusColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(textStatusColor)
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = taskUiState.status.name.lowercase().replaceFirstChar { it.uppercase() },
                style = Theme.textStyle.label.small,
                color = textStatusColor
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