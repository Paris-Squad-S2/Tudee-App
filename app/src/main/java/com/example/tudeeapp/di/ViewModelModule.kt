package com.example.tudeeapp.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tudeeapp.presentation.navigation.Destinations
import com.example.tudeeapp.presentation.navigation.Navigator
import com.example.tudeeapp.presentation.navigation.NavigatorImpl
import com.example.tudeeapp.presentation.screen.categories.CategoriesViewModel
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormViewModel
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsViewModel
import com.example.tudeeapp.presentation.screen.home.HomeViewModel
import com.example.tudeeapp.presentation.screen.onBoarding.OnBoardingViewModel
import com.example.tudeeapp.presentation.screen.task.TasksViewModel
import com.example.tudeeapp.presentation.screen.taskDetails.TaskDetailsViewModel
import com.example.tudeeapp.presentation.screen.taskForm.TaskFormViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::TaskDetailsViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::TaskFormViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::CategoryFormViewModel)
    viewModelOf(::TasksViewModel)
    single<Navigator> { NavigatorImpl(startGraph = Destinations.TudeeGraph) }
}