package com.example.tudeeapp.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screens {

    @Serializable
    data object Splash : Screens()

    @Serializable
    data object OnBoarding : Screens()

    @Serializable
    data object Home : Screens()

    @Serializable
    data object Task : Screens()

    @Serializable
    data object TaskForm : Screens()

    @Serializable
    data object TaskDetails : Screens()

    @Serializable
    data object Category : Screens()

    @Serializable
    data object CategoriesForm : Screens()

    @Serializable
    data class CategoryDetails(val id: Long) : Screens()

    @Serializable
    data class TaskManagement(val taskId: Int? = null) : Screens()

}
