package com.example.tudeeapp.presentation.screen.taskManagement.components

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
import com.example.tudeeapp.presentation.screen.taskManagement.TaskPriorityUiState
import com.example.tudeeapp.presentation.common.components.PriorityButton
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices  
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun PriorityRow(
    selectedPriority: TaskPriorityUiState,
    onPrioritySelected: (TaskPriorityUiState) -> Unit,
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
                text = stringResource(R.string.high),
                icon = painterResource(R.drawable.ic_flag),
                backgroundColor = if (selectedPriority == TaskPriorityUiState.HIGH)
                    Theme.colors.status.pinkAccent else Theme.colors.surfaceColors.surfaceLow,
                contentColor = if (selectedPriority == TaskPriorityUiState.HIGH)
                    Color.White else Theme.colors.text.hint,
                onClick = {
                    onPrioritySelected(
                        if (selectedPriority == TaskPriorityUiState.HIGH) TaskPriorityUiState.NONE else TaskPriorityUiState.HIGH
                    )
                }
            )
            PriorityButton(
                text = stringResource(R.string.medium),
                icon = painterResource(R.drawable.ic_worning),
                backgroundColor = if (selectedPriority == TaskPriorityUiState.MEDIUM)
                    Theme.colors.status.yellowAccent else Theme.colors.surfaceColors.surfaceLow,
                contentColor = if (selectedPriority == TaskPriorityUiState.MEDIUM)
                    Color.White else Theme.colors.text.hint,
                onClick = {
                    onPrioritySelected(
                        if (selectedPriority == TaskPriorityUiState.MEDIUM) TaskPriorityUiState.NONE else TaskPriorityUiState.MEDIUM
                    )
                }
            )
            PriorityButton(
                text = stringResource(R.string.low),
                icon = painterResource(R.drawable.ic_low),
                backgroundColor = if (selectedPriority == TaskPriorityUiState.LOW)
                    Theme.colors.status.greenAccent else Theme.colors.surfaceColors.surfaceLow,
                contentColor = if (selectedPriority == TaskPriorityUiState.LOW)
                    Color.White else Theme.colors.text.hint,
                onClick = {
                    onPrioritySelected(
                        if (selectedPriority == TaskPriorityUiState.LOW) TaskPriorityUiState.NONE else TaskPriorityUiState.LOW
                    )
                }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
private fun PriorityRowPreview() {
    BasePreview {
        PriorityRow(
            selectedPriority = TaskPriorityUiState.NONE,
            onPrioritySelected = {}
        )
    }
}