package com.example.tudeeapp.presentation.screen.onBoardScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.addOnBoardScreenRoute(){
    composable<Screen.OnBoard> {
        OnBoardScreen()
    }
}

fun NavController.navigateToOnBoardScreen(){
    navigate(Screen.OnBoard)
}

