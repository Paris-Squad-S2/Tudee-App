package com.example.tudeeapp.presentation.screen.taskScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.addTaskScreenRoute() {
    composable<Screen.TaskScreen> {
        val args = it.toRoute<Screen.TaskScreen>()
        TaskScreen(
            taskId = args.taskId,
            taskTitle = args.taskTitle
        )
    }
}

fun NavController.navigateToTaskScreen(
    taskId: Int,
    taskTitle: String
) {
    navigate(
        Screen.TaskScreen(
            taskId = taskId,
            taskTitle = taskTitle
        )
    )
}

