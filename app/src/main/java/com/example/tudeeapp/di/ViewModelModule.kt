package com.example.tudeeapp.di

import com.example.tudeeapp.MainViewModel
import com.example.tudeeapp.presentation.screen.categories.CategoriesViewModel
import com.example.tudeeapp.presentation.screen.onBoarding.OnboardingViewModel
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::TaskDetailsViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::OnboardingViewModel)
}