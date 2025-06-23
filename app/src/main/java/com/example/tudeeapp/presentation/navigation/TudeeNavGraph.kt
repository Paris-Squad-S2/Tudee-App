package com.example.tudeeapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.tudeeapp.presentation.common.extentions.ObserveAsEvents
import org.koin.compose.koinInject

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TudeeNavGraph(navController: NavHostController, navigator: Navigator = koinInject()) {

    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            NavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    CompositionLocalProvider(
        LocalNavController provides navController,
    ) {

        NavHost(
            navController = navController,
            startDestination = navigator.startGraph,
        ) {
            buildTudeeNavGraph()
        }

    }
}