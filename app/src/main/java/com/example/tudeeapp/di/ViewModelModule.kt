package com.example.tudeeapp.di

import com.example.tudeeapp.MainViewModel
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::CategoryFormViewModel)
}