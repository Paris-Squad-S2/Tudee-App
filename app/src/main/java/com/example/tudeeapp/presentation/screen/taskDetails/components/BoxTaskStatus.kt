package com.example.tudeeapp.presentation.screen.taskDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.domain.models.TaskStatus
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.taskDetails.TaskUiState

@Composable
fun BoxTaskStatus(taskUiState: TaskUiState) {

    val textStatusColor = when (taskUiState.status) {
        TaskStatus.TO_DO -> Theme.colors.status.yellowAccent
        TaskStatus.DONE -> Theme.colors.status.greenAccent
        TaskStatus.IN_PROGRESS -> Theme.colors.status.purpleAccent
    }

    val backgroundStatusColor = when (taskUiState.status) {
        TaskStatus.TO_DO -> Theme.colors.status.yellowVariant
        TaskStatus.DONE -> Theme.colors.status.greenVariant
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