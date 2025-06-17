package com.example.tudeeapp.presentation.bottomSheets.addEditTask.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.TaskPriorityUiState
import com.example.tudeeapp.presentation.common.components.PriorityButton
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices  
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.domain.models.TaskPriority

@Composable
fun PriorityRow(
    selectedPriority: TaskPriorityUiState,
    onPrioritySelected: (TaskPriority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.priority),
            style = Theme.textStyle.title.medium,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PriorityButton(
                text = "High",
                icon = painterResource(R.drawable.ic_flag),
                backgroundColor = if (selectedPriority is TaskPriorityUiState.Selected && selectedPriority.priority == TaskPriority.HIGH)
                    Theme.colors.primary else Theme.colors.surfaceColors.surfaceLow,
                contentColor = if (selectedPriority is TaskPriorityUiState.Selected && selectedPriority.priority == TaskPriority.HIGH)
                    Color.White else Theme.colors.text.hint,
                onClick = { onPrioritySelected(TaskPriority.HIGH) }
            )
            PriorityButton(
                text = "Medium",
                icon = painterResource(R.drawable.ic_worning),
                backgroundColor = if (selectedPriority is TaskPriorityUiState.Selected && selectedPriority.priority == TaskPriority.MEDIUM)
                    Theme.colors.status.pinkAccent else Theme.colors.surfaceColors.surfaceLow,
                contentColor = if (selectedPriority is TaskPriorityUiState.Selected && selectedPriority.priority == TaskPriority.MEDIUM)
                    Color.White else Theme.colors.text.hint,
                onClick = { onPrioritySelected(TaskPriority.MEDIUM) }
            )
            PriorityButton(
                text = "Low",
                icon = painterResource(R.drawable.ic_low),
                backgroundColor = if (selectedPriority is TaskPriorityUiState.Selected && selectedPriority.priority == TaskPriority.LOW)
                    Theme.colors.status.greenAccent else Theme.colors.surfaceColors.surfaceLow,
                contentColor = if (selectedPriority is TaskPriorityUiState.Selected && selectedPriority.priority == TaskPriority.LOW)
                    Color.White else Theme.colors.text.hint,
                onClick = { onPrioritySelected(TaskPriority.LOW) }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
private fun PriorityRowPreview() {
    BasePreview {
        PriorityRow(
            selectedPriority = TaskPriorityUiState.Selected(TaskPriority.HIGH),
            onPrioritySelected = {}
        )
    }
}