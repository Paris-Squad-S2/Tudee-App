package com.example.tudeeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tudeeapp.presentation.feature.categoriesForm.CategoryFormScreen
import com.example.tudeeapp.presentation.feature.category.CategoryScreen
import com.example.tudeeapp.presentation.feature.categoryDetails.CategoryDetailsScreen
import com.example.tudeeapp.presentation.feature.home.HomeScreen
import com.example.tudeeapp.presentation.feature.onBoarding.OnBoardScreen
import com.example.tudeeapp.presentation.feature.splash.SplashScreen
import com.example.tudeeapp.presentation.feature.task.TaskScreen
import com.example.tudeeapp.presentation.feature.taskDetails.TaskDetailsScreen
import com.example.tudeeapp.presentation.feature.taskForm.TaskFormScreen

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

            composable<Screens.Home> {
                val args = it.toRoute<Screens.Home>()
                HomeScreen(userName = args.userName)
            }

            composable<Screens.Task> {
                val args = it.toRoute<Screens.Task>()
                TaskScreen(
                    taskId = args.taskId,
                    taskTitle = args.taskTitle
                )
            }

            composable<Screens.TaskForm> {
                TaskFormScreen()
            }

            composable<Screens.TaskDetails> {
                TaskDetailsScreen()
            }

            composable<Screens.Category> {
                CategoryScreen()
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


