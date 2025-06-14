package com.example.tudeeapp.presentation.screen.homeScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.addHomeScreenRoute(){
    composable<Screen.Home> {
        val args = it.toRoute<Screen.Home>()
        HomeScreen(userName = args.userName)
    }
}


fun NavController.navigateToHomeScreen(userName : String){
    navigate(Screen.Home(
        userName = userName
    ))
}