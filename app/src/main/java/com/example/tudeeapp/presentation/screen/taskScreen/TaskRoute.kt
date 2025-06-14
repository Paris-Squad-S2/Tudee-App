package com.example.tudeeapp.presentation.screen.taskScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.addTaskScreenRoute(){
    composable<Screen.TaskScreen> {
        TaskScreen()
    }
}

fun NavController.navigateToTaskScreen(){
    navigate(Screen.TaskScreen)
}

