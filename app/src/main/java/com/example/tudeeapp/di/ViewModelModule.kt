package com.example.tudeeapp.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tudeeapp.MainViewModel
import com.example.tudeeapp.presentation.screen.onBoarding.OnboardingViewModel
import com.example.tudeeapp.presentation.screen.task.TaskViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::TaskViewModel)
}