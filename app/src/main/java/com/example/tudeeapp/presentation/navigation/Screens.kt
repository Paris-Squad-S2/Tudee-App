package com.example.tudeeapp.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screens {

    @Serializable
    data object Splash : Screens()

    @Serializable
    data object OnBoarding : Screens()

    @Serializable
    data class Home(val userName :String) : Screens()

    @Serializable
    data class Task(val taskId : Int, val taskTitle : String) : Screens()

    @Serializable
    data object TaskForm : Screens()

    @Serializable
    data object TaskDetails : Screens()

    @Serializable
    data object Category : Screens()

    @Serializable
    data object CategoriesForm : Screens()

    @Serializable
    data object CategoryDetails : Screens()

}
