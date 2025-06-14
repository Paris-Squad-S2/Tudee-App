package com.example.tudeeapp.presentation.screen.taskDetailsScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen
import com.example.tudeeapp.presentation.screen.taskFormScreen.TaskFormScreen

fun NavGraphBuilder.addTaskDetailsScreenRoute(){
    composable<Screen.TaskDetailsScreen> {
        TaskDetailsScreen()
    }
}

fun NavController.navigateToTaskDetailsScreen(){
    navigate(Screen.TaskDetailsScreen)
}