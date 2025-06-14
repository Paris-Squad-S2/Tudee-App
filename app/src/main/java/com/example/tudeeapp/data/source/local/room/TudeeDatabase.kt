package com.example.tudeeapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity

@Database(entities = [TaskEntity::class, CategoryEntity::class], version = 1)
abstract class TudeeDatabase: RoomDatabase() {
    abstract fun taskDao() :TaskDao
    abstract fun categoryDao(): CategoryDao
}