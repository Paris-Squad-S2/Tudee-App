package com.example.tudeeapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tudeeapp.presentation.common.components.SnackBar
import com.example.tudeeapp.presentation.common.components.SnackBarState
import com.example.tudeeapp.presentation.common.components.TudeeNavigationBar
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.categories.CategoriesScreen
import com.example.tudeeapp.presentation.screen.taskManagement.TaskManagementBottomSheet
import com.example.tudeeapp.presentation.screen.categoriesForm.AddCategoryScreen
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsScreen
import com.example.tudeeapp.presentation.screen.home.HomeScreen
import com.example.tudeeapp.presentation.screen.onBoarding.OnBoardScreen
import com.example.tudeeapp.presentation.screen.onBoarding.OnboardingViewModel
import com.example.tudeeapp.presentation.screen.onBoarding.onboardingPages
import com.example.tudeeapp.presentation.screen.splash.SplashScreen
import com.example.tudeeapp.presentation.screen.task.TaskScreen
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsScreen
import com.example.tudeeapp.presentation.screen.taskForm.TaskFormScreen
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }
val LocalSnackBarState = compositionLocalOf<SnackBarState> { error("No SnackBarState provided") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TudeeNavGraph() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination
    val snackBarState = remember { SnackBarState() }

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalSnackBarState provides snackBarState
    ) {

        Box {
            TudeeScaffold(
                bottomBar = {
                    if ((currentRoute?.route != null) && listOf(
                            Screens.Home::class.qualifiedName,
                            Screens.Task::class.qualifiedName,
                            Screens.Category::class.qualifiedName
                        ).contains(currentRoute.route)
                    )
                        TudeeNavigationBar(
                            onItemClick = { navItem ->
                                navController.navigate(navItem.screen)
                            }
                        )
                },
                contentBackground = Theme.colors.surfaceColors.surface
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Splash,
                ) {

                    composable<Screens.Splash> { SplashScreen() }
                    composable<Screens.OnBoarding> {
                        val onboardingViewModel: OnboardingViewModel = koinViewModel()
                        OnBoardScreen(
                            onboardingViewModel,
                            onboardingPages()
                        )
                    }
                    composable<Screens.Home> { HomeScreen() }
                    composable<Screens.Task> { TaskScreen() }
                    composable<Screens.Category> { CategoriesScreen() }
                    composable<Screens.TaskForm> { TaskFormScreen() }
                    composable<Screens.TaskDetails> { TaskDetailsScreen() }
                    dialog<Screens.AddCategoryScreen> {
                        AddCategoryScreen()
                    }

                    composable<Screens.CategoryDetails> {
                        val args = it.toRoute<Screens.CategoryDetails>()
                        CategoryDetailsScreen(args.id)
                    }
                    dialog<Screens.TaskManagement> { TaskManagementBottomSheet() }
                }
            }

            if (snackBarState.isVisible) {
                AnimatedVisibility(
                    visible = snackBarState.isVisible,
                    enter = fadeIn(animationSpec = tween(snackBarState.durationMillis)),
                    exit = fadeOut(animationSpec = tween(snackBarState.durationMillis))
                ) {
                    SnackBar(
                        Modifier
                            .statusBarsPadding()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        text = snackBarState.message,
                        isSuccess = snackBarState.isSuccess,
                        onClick = { snackBarState.hide() }
                    )
                }
                LaunchedEffect(Unit) {
                    delay(snackBarState.durationMillis.toLong())
                    snackBarState.hide()
                }
            }
        }
    }
}

