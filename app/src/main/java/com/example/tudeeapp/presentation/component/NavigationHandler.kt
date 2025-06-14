package com.example.tudeeapp.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }


sealed class Screen {
    @Serializable
    data object First : Screen()

    @Serializable
    data class Second(val name: String) : Screen()

    @Serializable
    data object TaskScreen : Screen()

    @Serializable
    data object CategoryScreen : Screen()

}
