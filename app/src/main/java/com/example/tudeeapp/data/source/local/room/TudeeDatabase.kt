package com.example.tudeeapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tudeeapp.data.source.local.room.TudeeDbMigrations.MIGRATION_1_2
import com.example.tudeeapp.data.source.local.room.TudeeDbMigrations.MIGRATION_2_3
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity

@Database(
    entities = [TaskEntity::class, CategoryEntity::class],
    version = 3,
)
@TypeConverters(Converters::class)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "tudee_db"

        @Volatile
        private var instance: TudeeDatabase? = null

        fun getInstance(context: Context): TudeeDatabase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): TudeeDatabase {
            return Room.databaseBuilder(context, TudeeDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
        }
    }
}