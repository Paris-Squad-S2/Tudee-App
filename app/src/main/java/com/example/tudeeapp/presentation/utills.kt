package com.example.tudeeapp.presentation

import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import com.example.tudeeapp.domain.models.Category

class AppInitializer(
    private val appPreferences: AppPreferences,
    private val taskServices: TaskServices,
    private val predefinedCategories: List<Category>
) {
     fun initializeApp() {
        if (appPreferences.isFirstLaunch()) {
            taskServices.insertPredefinedCategories(predefinedCategories)
            appPreferences.setFirstLaunchDone()
        }
    }
}