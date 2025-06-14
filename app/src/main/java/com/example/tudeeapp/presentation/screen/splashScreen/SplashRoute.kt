package com.example.tudeeapp.presentation.screen.splashScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudeeapp.presentation.component.Screen

fun NavGraphBuilder.addSplashScreenRoute(){
    composable<Screen.Splash> {
        SplashScreen()
    }
}
