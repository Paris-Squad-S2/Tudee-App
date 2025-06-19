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
    data class Task(val tasksStatus: String="") : Screens()

    @Serializable
    data object TaskForm : Screens()

    @Serializable
    data class TaskDetails(val taskId: Long=1L) : Screens()

    @Serializable
    data object Category : Screens()

    @Serializable
    data object CategoriesForm : Screens()

    @Serializable
    data class CategoryDetails(val id: Long) : Screens()

    @Serializable
    data class TaskManagement(val taskId: Long? = null) : Screens()

}
