package com.example.tudeeapp.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tudeeapp.MainViewModel
import com.example.tudeeapp.presentation.screen.categories.CategoriesViewModel
import com.example.tudeeapp.presentation.screen.onBoarding.OnboardingViewModel
import com.example.tudeeapp.presentation.screen.taskManagement.TaskManagementViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsViewModel

@RequiresApi(Build.VERSION_CODES.O)
val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::TaskManagementViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::TaskViewModel)
}