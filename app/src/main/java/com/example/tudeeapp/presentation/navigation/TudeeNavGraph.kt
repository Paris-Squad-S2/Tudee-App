package com.example.tudeeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormScreen
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsScreen
import com.example.tudeeapp.presentation.screen.onBoarding.OnBoardScreen
import com.example.tudeeapp.presentation.screen.splash.SplashScreen
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsScreen
import com.example.tudeeapp.presentation.screen.taskForm.TaskFormScreen

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }

@Composable
fun TudeeNavGraph() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {

        NavHost(navController = navController, startDestination = Screens.Splash::class) {

            composable<Screens.Splash> {
                SplashScreen()
            }

            composable<Screens.OnBoarding> {
                OnBoardScreen()
            }

            composable<Screens.MainNav> {
                MainNavGraph()
            }

            composable<Screens.TaskForm> {
                TaskFormScreen()
            }

            composable<Screens.TaskDetails> {
                TaskDetailsScreen()
            }

            composable<Screens.CategoriesForm> {
                CategoryFormScreen()
            }

            composable<Screens.CategoryDetails> {
                CategoryDetailsScreen()
            }

        }
    }
}

