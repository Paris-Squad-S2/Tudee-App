package com.example.tudeeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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

    CompositionLocalProvider(LocalMainNavController provides mainNavController) {
        TudeeScaffold(
            showBottomBar = true,
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
                startDestination = Screens.Home("John Doe"),
            ) {

                composable<Screens.Home> {
                    val args = it.toRoute<Screens.Home>()
                    HomeScreen(userName = args.userName)
                }

                composable<Screens.Task> {
                    val args = it.toRoute<Screens.Task>()
                    TaskScreen(args.taskId, args.taskTitle)
                }

                composable<Screens.Category> {
                    CategoryScreen()
                }
            }
        }
    }
}

