package com.example.tudeeapp.di

import com.example.tudeeapp.data.TaskServicesImpl
import com.example.tudeeapp.data.mapper.DataConstant
import com.example.tudeeapp.data.source.local.sharedPreferences.AppPreferences
import com.example.tudeeapp.domain.TaskServices
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single { DataConstant }
    single<TaskServices> { TaskServicesImpl(get(), get(),get(),get()) }
    single { AppPreferences(androidApplication().applicationContext) }
}