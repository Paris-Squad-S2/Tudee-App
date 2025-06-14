package com.example.tudeeapp.App

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.tudeeapp.presentation.component.Screen
import com.example.tudeeapp.presentation.screen.categoriesFormScreen.addCategoriesFormScreenRoute
import com.example.tudeeapp.presentation.screen.category.addCategoryScreenRoute
import com.example.tudeeapp.presentation.screen.categoryDetailsScreen.addCategoryDetailsScreenRoute
import com.example.tudeeapp.presentation.screen.homeScreen.addHomeScreenRoute
import com.example.tudeeapp.presentation.screen.onBoardScreen.addOnBoardScreenRoute
import com.example.tudeeapp.presentation.screen.splashScreen.addSplashScreenRoute
import com.example.tudeeapp.presentation.screen.taskDetailsScreen.addTaskDetailsScreenRoute
import com.example.tudeeapp.presentation.screen.taskFormScreen.addTaskFormScreenRoute
import com.example.tudeeapp.presentation.screen.taskScreen.addTaskScreenRoute

@Composable
fun TudeeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash::class) {
        addSplashScreenRoute()
        addOnBoardScreenRoute()
        addHomeScreenRoute()
        addTaskScreenRoute()
        addTaskScreenRoute()
        addCategoryScreenRoute()
        addCategoriesFormScreenRoute()
        addCategoryDetailsScreenRoute()
        addTaskFormScreenRoute()
        addTaskDetailsScreenRoute()
    }
}


