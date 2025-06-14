package com.example.tudeeapp.data.source.local.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity

@Database(
    entities = [TaskEntity::class, CategoryEntity::class], version = 1,
     exportSchema = true
)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "tasks_db"
        @Volatile
        private var instance: TudeeDatabase? = null

        fun getInstance(context: Context): TudeeDatabase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): TudeeDatabase {
            return Room.databaseBuilder(context, TudeeDatabase::class.java, DATABASE_NAME).build()
        }
    }
}