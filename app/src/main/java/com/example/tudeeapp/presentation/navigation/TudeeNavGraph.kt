package com.example.tudeeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudeeapp.presentation.common.components.TudeeNavigationBar
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormScreen
import com.example.tudeeapp.presentation.screen.category.CategoryScreen
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsScreen
import com.example.tudeeapp.presentation.screen.home.HomeScreen
import com.example.tudeeapp.presentation.screen.onBoarding.OnBoardScreen
import com.example.tudeeapp.presentation.screen.splash.SplashScreen
import com.example.tudeeapp.presentation.screen.task.TaskScreen
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsScreen
import com.example.tudeeapp.presentation.screen.taskForm.TaskFormScreen

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }

@Composable
fun TudeeNavGraph() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value

    CompositionLocalProvider(LocalNavController provides navController) {

        val currentRoute = backStackEntry?.destination?.route?.substringBefore('?')
        val selectedRouteIndex = listOf(
            Screens.Home::class.qualifiedName,
            Screens.Task::class.qualifiedName,
            Screens.Category::class.qualifiedName
        ).indexOf(currentRoute)

        TudeeScaffold(
            bottomBar = {
                if (selectedRouteIndex != -1)
                    TudeeNavigationBar(
                        onItemClick = { navItem ->
                            navController.navigate(navItem.screen)
                        },
                        selected = selectedRouteIndex
                    )
            },
            contentBackground = Theme.colors.surfaceColors.surface
        ) {
            NavHost(
                navController = navController,
                startDestination = Screens.Splash,
            ) {

                composable<Screens.Splash> {
                    SplashScreen()
                }

                composable<Screens.OnBoarding> {
                    OnBoardScreen()
                }

                composable<Screens.Home> {
                    HomeScreen()
                }

                composable<Screens.Task> {
                    TaskScreen()
                }

                composable<Screens.Category> {
                    CategoryScreen()
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
}

