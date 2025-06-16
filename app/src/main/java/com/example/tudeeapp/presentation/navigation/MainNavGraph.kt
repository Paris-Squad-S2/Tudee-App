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
import com.example.tudeeapp.presentation.screen.category.CategoryScreen
import com.example.tudeeapp.presentation.screen.home.HomeScreen
import com.example.tudeeapp.presentation.screen.task.TaskScreen


val LocalMainNavController =
    compositionLocalOf<NavHostController> { error("No Nav Controller Found") }

@Composable
fun MainNavGraph() {
    val mainNavController = rememberNavController()
    val backStackEntry = mainNavController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination

    CompositionLocalProvider(LocalMainNavController provides mainNavController) {
        TudeeScaffold(
            showBottomBar = (currentRoute?.route != null) && listOf(
                Screens.Home::class.qualifiedName,
                Screens.Task::class.qualifiedName,
                Screens.Category::class.qualifiedName
            ).contains(currentRoute.route),
            bottomBar = {
                TudeeNavigationBar(
                    onItemClick = { navItem ->
                        mainNavController.navigate(navItem.screen)
                    }
                )
            },
            contentBackground = Theme.colors.surfaceColors.surface
        ) { snakeBar ->

            NavHost(
                navController = mainNavController,
                startDestination = Screens.Home,
            ) {

                composable<Screens.Home> {
                    HomeScreen()
                }

                composable<Screens.Task> {
                    TaskScreen()
                }

                composable<Screens.Category> {
                    CategoryScreen()
                }
            }
        }
    }
}

