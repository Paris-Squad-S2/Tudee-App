package com.example.tudeeapp.presentation.screen.firstScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.ScreenNav

fun NavGraphBuilder.navigateToFirst(){
    composable<ScreenNav.First> {
        FirstScreen()
    }
}

fun NavController.navigateToFirstScreen(name:String){
    navigate(ScreenNav.Second(name =name))
}
