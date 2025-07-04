package com.example.tudeeapp.presentation

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.presentation.common.components.SnackBar
import com.example.tudeeapp.presentation.common.components.SnackBarState
import com.example.tudeeapp.presentation.common.components.TudeeNavigationBar
import com.example.tudeeapp.presentation.common.components.TudeeScaffold
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme
import com.example.tudeeapp.presentation.navigation.TudeeNavGraph
import kotlinx.coroutines.delay

val LocalSnackBarState = compositionLocalOf<SnackBarState> { error("No SnackBarState provided") }
val LocalThemeState =
    compositionLocalOf<MutableState<TudeeThemeMode>> { error("No TaskManagementState provided") }

@Composable
fun TudeeAppScaffold() {
    val navController = rememberNavController()
    val snackBarState = remember { SnackBarState() }
    val context = LocalContext.current
    val appPrefs = remember { AppPreferences(context) }

    val isSystemInDarkTheme = isSystemInDarkTheme()

    val themeMode = rememberSaveable {
        val isDarkTheme = appPrefs.isDarkTheme()
        val currentTheme = if (isDarkTheme == 1) {
            TudeeThemeMode.DARK
        } else if (isDarkTheme == 0) {
            TudeeThemeMode.LIGHT
        } else {
            if (isSystemInDarkTheme) TudeeThemeMode.DARK else TudeeThemeMode.LIGHT
        }
        mutableStateOf(currentTheme)
    }

    val view = LocalView.current
    val activity = context as? ComponentActivity

    LaunchedEffect(themeMode.value) {
        val darkIcons = themeMode.value == TudeeThemeMode.LIGHT
        val isDarkTheme = appPrefs.isDarkTheme()
        themeMode.value = if (isDarkTheme == 1) {
            TudeeThemeMode.DARK
        } else if (isDarkTheme == 0) {
            TudeeThemeMode.LIGHT
        } else {
            if (isSystemInDarkTheme) TudeeThemeMode.DARK else TudeeThemeMode.LIGHT
        }

        activity?.window?.also { window ->
            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = darkIcons
                isAppearanceLightNavigationBars = darkIcons
            }
        }
    }


    CompositionLocalProvider(
        LocalSnackBarState provides snackBarState,
        LocalThemeState provides themeMode
    ) {
        TudeeTheme(
            isDarkTheme = themeMode.value == TudeeThemeMode.DARK
        ) {
            TudeeScaffold(
                modifier = Modifier
                    .background(Theme.colors.surfaceColors.surface)
                    .navigationBarsPadding(),
                bottomBar = { TudeeNavigationBar(navController) },
                contentBackground = Theme.colors.surfaceColors.surface
            ) {
                TudeeNavGraph(navController)
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