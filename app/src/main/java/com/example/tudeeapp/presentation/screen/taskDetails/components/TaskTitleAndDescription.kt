package com.example.tudeeapp.presentation.screen.taskDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.taskDetails.TaskUiState

@Composable
fun TaskTitleAndDescription(taskUiState: TaskUiState) {
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
        color = Theme.colors.text.body,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}