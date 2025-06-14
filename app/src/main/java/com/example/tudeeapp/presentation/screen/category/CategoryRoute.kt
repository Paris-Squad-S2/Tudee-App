package com.example.tudeeapp.presentation.screen.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.navigateToCategoryScreen(){
    composable<Screen.CategoryScreen> {
        CategoryScreen()
    }
}

fun NavController.navigateToTaskScreen(){
    navigate(Screen.TaskScreen)
}