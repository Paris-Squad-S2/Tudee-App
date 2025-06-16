package com.example.tudeeapp.di

import com.example.tudeeapp.data.source.local.room.TudeeDatabase
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single { TudeeDatabase.getInstance(androidApplication().applicationContext) }
    single<TaskDao> { get<TudeeDatabase>().taskDao() }
    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
}