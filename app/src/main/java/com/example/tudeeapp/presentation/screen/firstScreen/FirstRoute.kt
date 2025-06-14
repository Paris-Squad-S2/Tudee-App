package com.example.tudeeapp.presentation.screen.firstScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.navigateToFirst(){
    composable<Screen.First> {
        FirstScreen()
    }
}

fun NavController.navigateToSecondScreen(name:String){
    navigate(Screen.Second(name =name))
}
