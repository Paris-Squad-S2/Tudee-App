package com.example.tudeeapp.presentation.screen.taskFormScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.addTaskFormScreenRoute(){
    composable<Screen.TaskFormScreen> {
        TaskFormScreen()
    }
}

fun NavController.navigateToTaskFormScreen(){
    navigate(Screen.TaskFormScreen)
}