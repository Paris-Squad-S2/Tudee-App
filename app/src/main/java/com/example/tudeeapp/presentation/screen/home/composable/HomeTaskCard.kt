package com.example.tudeeapp.presentation.screen.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.tudeeapp.presentation.common.components.TaskCard
import com.example.tudeeapp.presentation.screen.home.state.TaskUiState
import com.example.tudeeapp.presentation.screen.home.utils.getPriorityColor

@Composable
fun HomeTaskCard(
    modifier: Modifier = Modifier,
    task: TaskUiState,
    onClickItem: () -> Unit
) {
    TaskCard(
        categoryIcon = task.categoryIcon,
        isCategoryPredefined = task.isCategoryPredefined,
        title = task.title,
        date = "",
        subtitle = task.description,
        priorityLabel = task.priority.name.lowercase(),
        priorityIcon = painterResource(task.priorityResIcon),
        priorityColor = getPriorityColor(task.priority),
        isDated = false,
        modifier = modifier,
        onClickItem = onClickItem
    )
}

