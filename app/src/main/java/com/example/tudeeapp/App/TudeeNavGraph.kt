package com.example.tudeeapp.App

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tudeeapp.presentation.component.Screen
import com.example.tudeeapp.presentation.screen.category.CategoryScreen
import com.example.tudeeapp.presentation.screen.category.navigateToCategoryScreen
import com.example.tudeeapp.presentation.screen.firstScreen.navigateToFirst
import com.example.tudeeapp.presentation.screen.homeScreen.addHomeScreenRoute
import com.example.tudeeapp.presentation.screen.onBoardScreen.addOnBoardScreenRoute
import com.example.tudeeapp.presentation.screen.secondScreen.SecondScreen
import com.example.tudeeapp.presentation.screen.splashScreen.addSplashScreenRoute
import com.example.tudeeapp.presentation.screen.taskScreen.TaskScreen
import com.example.tudeeapp.presentation.screen.taskScreen.addTaskScreenRoute

@Composable
fun TudeeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash::class) {
        navigateToFirst()
        navigateToSecond()
        addSplashScreenRoute()
        addOnBoardScreenRoute()
        addHomeScreenRoute()
        addTaskScreenRoute()
        navigateToCategoryScreen()

    }
}

fun NavGraphBuilder.navigateToSecond(){
    composable<Screen.Second> {
        val args = it.toRoute<Screen.Second>()
        SecondScreen(name = args.name)
    }
}

