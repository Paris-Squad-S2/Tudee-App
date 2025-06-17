package com.example.tudeeapp.presentation.screen.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.Task
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.presentation.common.components.TaskCard
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun HomeTaskCard(modifier: Modifier = Modifier, onPriorityClick: () -> Unit, task: Task) {
    TaskCard(
        icon = painterResource(R.drawable.ic_adoration),
        title = task.title,
        date = task.createdDate.toString(),
        subtitle = task.description,
        priorityLabel = task.priority.name.lowercase(),
        priorityIcon = painterResource(getPriorityIcon(task.priority)),
        priorityColor = getPriorityColor(task.priority),
        isDated = false,
        modifier = modifier,
        onClickPriority = onPriorityClick,
    )
}

fun getPriorityIcon(priority: TaskPriority): Int {
    return when (priority) {
        TaskPriority.LOW -> R.drawable.ic_flag
        TaskPriority.MEDIUM -> android.R.drawable.ic_dialog_alert
        TaskPriority.HIGH -> R.drawable.ic_trade_down
    }
}

@Composable
fun getPriorityColor(priority: TaskPriority): Color {
    return when (priority) {
        TaskPriority.LOW -> Theme.colors.status.greenAccent
        TaskPriority.MEDIUM -> Theme.colors.status.greenAccent
        TaskPriority.HIGH -> Theme.colors.status.greenAccent
    }
}
