package com.example.tudeeapp.di

import android.content.Context
import com.example.tudeeapp.domain.models.Category
import org.koin.dsl.module

val predefinedCategoriesModule = module {
    single {
        val context: Context = get()
        listOf(
            Category(title = "Education", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Shopping", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Medical", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Gym", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Entertainment", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Cooking", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Family & friend", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Traveling", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Agriculture", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Coding", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Adoration", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Fixing bugs", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Cleaning", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Work", imageUri = "", isPredefined = true,id = 0),
            Category(title = "Budgeting", imageUri = "", isPredefined = true,id = 0),
        )
    }
}