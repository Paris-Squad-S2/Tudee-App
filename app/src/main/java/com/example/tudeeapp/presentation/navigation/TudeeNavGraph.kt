package com.example.tudeeapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.presentation.common.components.SnackBar
import com.example.tudeeapp.presentation.common.components.SnackBarState
import com.example.tudeeapp.presentation.common.components.TudeeNavigationBar
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme
import com.example.tudeeapp.presentation.screen.categories.CategoriesScreen
import com.example.tudeeapp.presentation.screen.categoriesForm.AddCategoryScreen
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormEditScreen
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsScreen
import com.example.tudeeapp.presentation.screen.home.HomeScreen
import com.example.tudeeapp.presentation.screen.onBoarding.OnBoardScreen
import com.example.tudeeapp.presentation.screen.onBoarding.onboardingPages
import com.example.tudeeapp.presentation.screen.splash.SplashScreen
import com.example.tudeeapp.presentation.screen.task.TaskScreen
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsScreen
import com.example.tudeeapp.presentation.screen.taskManagement.TaskManagementBottomSheet
import kotlinx.coroutines.delay

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }
val LocalSnackBarState = compositionLocalOf<SnackBarState> { error("No SnackBarState provided") }
val LocalThemeState = compositionLocalOf<MutableState<TudeeThemeMode>> { error("No TaskManagementState provided") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TudeeNavGraph() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    val snackBarState = remember { SnackBarState() }
    val context = LocalContext.current
    val appPrefs = remember { AppPreferences(context) }
    val themeMode = rememberSaveable {
        mutableStateOf(if (appPrefs.isDarkTheme()) TudeeThemeMode.DARK else TudeeThemeMode.LIGHT)
    }

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalSnackBarState provides snackBarState,
        LocalThemeState provides themeMode
    ) {

        val currentRoute = backStackEntry?.destination?.route?.substringBefore('?')
        val selectedRouteIndex = listOf(
            Screens.Home::class.qualifiedName,
            Screens.Task::class.qualifiedName,
            Screens.Category::class.qualifiedName
        ).indexOf(currentRoute)

        TudeeTheme(
            isDarkTheme = themeMode.value == TudeeThemeMode.DARK
        ) {
            TudeeScaffold(
                bottomBar = {
                    if (selectedRouteIndex != -1)
                        TudeeNavigationBar(
                            onItemClick = { navItem -> navController.navigate(navItem.screen) },
                            selected = selectedRouteIndex
                        )
                },
                contentBackground = Theme.colors.surfaceColors.surface
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Splash,
                ) {
                    composable<Screens.Splash> { SplashScreen() }
                    composable<Screens.OnBoarding> { OnBoardScreen(pages = onboardingPages()) }
                    composable<Screens.Home> { HomeScreen() }
                    composable<Screens.Task> { TaskScreen() }
                    composable<Screens.Category> { CategoriesScreen() }
                    dialog<Screens.TaskManagement> { TaskManagementBottomSheet() }
                    dialog<Screens.TaskDetails> { TaskDetailsScreen() }
                    dialog<Screens.AddCategoryScreen> { AddCategoryScreen() }
                    composable<Screens.CategoryDetails> { CategoryDetailsScreen() }
                    dialog<Screens.CategoryFormEditScreen> { CategoryFormEditScreen() }
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
}

enum class TudeeThemeMode(val value: Boolean) {
    DARK(true),
    LIGHT(false)
}