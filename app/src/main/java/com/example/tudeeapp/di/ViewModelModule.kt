package com.example.tudeeapp.di

import com.example.tudeeapp.MainViewModel
import com.example.tudeeapp.presentation.screen.categories.CategoriesViewModel
import com.example.tudeeapp.presentation.screen.home.HomeViewModel
import com.example.tudeeapp.presentation.screen.onBoarding.OnboardingViewModel
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsViewModel
import com.example.tudeeapp.presentation.screen.taskManagement.TaskManagementViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsViewModel
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormViewModel


val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::TaskDetailsViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::TaskManagementViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::CategoryFormViewModel)
}