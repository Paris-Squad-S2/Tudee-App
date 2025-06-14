package com.example.tudeeapp.presentation.screen.categoriesFormScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen


fun NavGraphBuilder.addCategoriesFormScreenRoute(){
    composable<Screen.CategoriesFormScreen> {
        CategoryFormScreen()
    }
}

fun NavController.navigateToCategoriesFormScreen(){
    navigate(Screen.CategoriesFormScreen)
}

