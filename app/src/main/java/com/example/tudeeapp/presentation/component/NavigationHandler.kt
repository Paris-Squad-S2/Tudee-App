package com.example.tudeeapp.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

val LocalNavController = compositionLocalOf<NavHostController> { error("No Nav Controller Found") }


sealed class ScreenNav {
    @Serializable
    data object First : ScreenNav()

    @Serializable
    data class Second(val name: String) : ScreenNav()

    @Serializable
    data object TaskScreen : ScreenNav()

    @Serializable
    data object CategoryScreen : ScreenNav()

}
