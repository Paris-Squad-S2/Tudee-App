package com.example.tudeeapp.presentation.screen.taskDetails.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.screen.taskDetails.TaskUiState

@Composable
fun StatusActionButtons(
    taskUiState: TaskUiState,
    onEditTaskClick: () -> Unit,
    onStatusChange: (TaskStatus) -> Unit
) {
    Row(modifier = Modifier.padding(top = 24.dp)) {
        TudeeButton(
            modifier = Modifier.height(56.dp),
            onClick = { onEditTaskClick() },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_pencil_edit_24),
                    contentDescription = null,
                )
            },
            variant = ButtonVariant.OutlinedButton,
        )
        Spacer(Modifier.width(4.dp))
        TudeeButton(
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
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