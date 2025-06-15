package com.example.tudeeapp.di

import com.example.tudeeapp.presentation.AppInitializer
import org.koin.dsl.module

val appInitializerModule = module {
    single {
        AppInitializer(
            appPreferences = get(),
            taskServices = get(),
            predefinedCategories = get()
        )
    }
}