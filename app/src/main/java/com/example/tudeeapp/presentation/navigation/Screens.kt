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
    data class Task(val tasksStatus: String = "") : Screens()

    @Serializable
    data class TaskDetails(val taskId: Long) : Screens()

    @Serializable
    data object Category : Screens()

    @Serializable
    data class CategoryForm(val categoryId: Long? = null) : Screens()

    @Serializable
    data class CategoryDetails(val categoryId: Long) : Screens()

    @Serializable
    data class TaskManagement(
        val taskId: Long? = null,
        val selectedDate:String ) : Screens()
}
