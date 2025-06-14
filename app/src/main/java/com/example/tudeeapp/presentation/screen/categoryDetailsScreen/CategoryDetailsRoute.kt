package com.example.tudeeapp.presentation.screen.categoryDetailsScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen
import com.example.tudeeapp.presentation.screen.categoriesFormScreen.CategoryFormScreen

fun NavGraphBuilder.addCategoryDetailsScreenRoute(){
    composable<Screen.CategoryDetailsScreen> {
        CategoryDetailsScreen()
    }
}

fun NavController.navigateToCategoryDetailsScreen(){
    navigate(Screen.CategoryDetailsScreen)
}
