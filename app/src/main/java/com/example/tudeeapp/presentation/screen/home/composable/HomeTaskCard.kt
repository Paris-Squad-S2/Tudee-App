package com.example.tudeeapp.presentation.screen.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.presentation.common.components.TaskCard
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.home.TaskUi

@Composable
fun HomeTaskCard(modifier: Modifier = Modifier, task: TaskUi) {
    TaskCard(
        icon = painterResource(R.drawable.ic_adoration),
        title = task.title,
        date = "",
        subtitle = task.description,
        priorityLabel = task.priority.name.lowercase(),
        priorityIcon = painterResource(task.resIconId),
        priorityColor = getPriorityColor(task.priority),
        isDated = false,
        modifier = modifier,
    )
}

@Composable
fun getPriorityColor(priority: TaskPriority): Color {
    return when (priority) {
        TaskPriority.LOW -> Theme.colors.status.greenAccent
        TaskPriority.MEDIUM -> Theme.colors.status.yellowAccent
        TaskPriority.HIGH -> Theme.colors.status.pinkAccent
    }
}
