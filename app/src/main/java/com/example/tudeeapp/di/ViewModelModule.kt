package com.example.tudeeapp.di

import com.example.tudeeapp.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.example.tudeeapp.presentation.screen.categoryDetails.CategoryDetailsViewModel

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::CategoryDetailsViewModel)
}