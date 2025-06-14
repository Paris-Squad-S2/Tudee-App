package com.example.tudeeapp.di

import androidx.room.Room
import com.example.tudeeapp.data.source.local.room.TudeeDatabase
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import org.koin.dsl.module

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            TudeeDatabase::class.java,
            "tasks_db"
            ).build()
    }

    single<TaskDao> { get<TudeeDatabase>().taskDao() }
    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
}