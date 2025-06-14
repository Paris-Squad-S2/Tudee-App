package com.example.tudeeapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tudeeapp.presentation.component.ScreenNav
import com.example.tudeeapp.presentation.screen.firstScreen.FirstScreen
import com.example.tudeeapp.presentation.screen.secondScreen.SecondScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ScreenNav.First::class) {

        navigateToFirst()
        navigateToSecond()
    }
}

fun NavGraphBuilder.navigateToFirst(){
    composable<ScreenNav.First> {
        FirstScreen()
    }
}

fun NavGraphBuilder.navigateToSecond(){
    composable<ScreenNav.Second> {
        val args = it.toRoute<ScreenNav.Second>()
        SecondScreen(name = args.name)
    }
}

