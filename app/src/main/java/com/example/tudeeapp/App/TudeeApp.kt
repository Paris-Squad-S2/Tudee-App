package com.example.tudeeapp.App

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.tudeeapp.presentation.component.LocalNavController

@Composable
fun TudeeApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        AppNavGraph(navController)
    }
}