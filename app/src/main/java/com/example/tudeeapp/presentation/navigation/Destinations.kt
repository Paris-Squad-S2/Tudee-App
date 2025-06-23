package com.example.tudeeapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
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
import kotlinx.serialization.Serializable

sealed interface Destinations : Graph {

    @Serializable
    data object TudeeGraph : Graph

    @Serializable
    data object Splash : Destination

    @Serializable
    data object OnBoarding : Destination

    @Serializable
    data object Home : Destination

    @Serializable
    data class Tasks(val tasksStatus: String = "") : Destination

    @Serializable
    data class TaskDetails(val taskId: Long) : Destination

    @Serializable
    data object Category : Destination

    @Serializable
    data class CategoryForm(val categoryId: Long? = null) : Destination

    @Serializable
    data class CategoryDetails(val categoryId: Long) : Destination

    @Serializable
    data class TaskManagement(val taskId: Long? = null, val selectedDate: String) : Destination
}

fun NavGraphBuilder.buildTudeeNavGraph() {
    navigation<Destinations.TudeeGraph>(startDestination = Destinations.Splash) {
        composable<Destinations.Splash> { SplashScreen() }
        composable<Destinations.OnBoarding> { OnBoardScreen(pages = onboardingPages()) }
        composable<Destinations.Home> { HomeScreen() }
        composable<Destinations.Tasks> { TasksScreen() }
        composable<Destinations.Category> { CategoriesScreen() }
        dialog<Destinations.TaskManagement> { TaskManagementBottomSheet() }
        dialog<Destinations.TaskDetails> { TaskDetailsScreen() }
        composable<Destinations.CategoryDetails> { CategoryDetailsScreen() }
        dialog<Destinations.CategoryForm> { CategoryForm() }
    }
}
