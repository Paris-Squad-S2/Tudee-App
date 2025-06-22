package com.example.tudeeapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.tudeeapp.presentation.screen.categories.CategoriesScreen
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryForm
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsScreen
import com.example.tudeeapp.presentation.screen.home.HomeScreen
import com.example.tudeeapp.presentation.screen.onBoarding.OnBoardScreen
import com.example.tudeeapp.presentation.screen.onBoarding.onboardingPages
import com.example.tudeeapp.presentation.screen.splash.SplashScreen
import com.example.tudeeapp.presentation.screen.task.TasksScreen
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsScreen
import com.example.tudeeapp.presentation.screen.taskManagement.TaskManagementBottomSheet

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TudeeNavGraph(navController: NavHostController) {

    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.Splash,
        ) {
            composable<Screens.Splash> { SplashScreen() }
            composable<Screens.OnBoarding> { OnBoardScreen(pages = onboardingPages()) }
            composable<Screens.Home> { HomeScreen() }
            composable<Screens.Tasks> { TasksScreen() }
            composable<Screens.Category> { CategoriesScreen() }
            dialog<Screens.TaskManagement> { TaskManagementBottomSheet() }
            dialog<Screens.TaskDetails> { TaskDetailsScreen() }
            composable<Screens.CategoryDetails> { CategoryDetailsScreen() }
            dialog<Screens.CategoryForm> {
                CategoryForm()
            }
        }
    }
}