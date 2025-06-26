package com.example.tudeeapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tudeeapp.data.source.local.room.dao.CategoryDao
import com.example.tudeeapp.data.source.local.room.dao.TaskDao
import com.example.tudeeapp.data.source.local.room.entity.CategoryEntity
import com.example.tudeeapp.data.source.local.room.entity.TaskEntity

@Database(
    entities = [TaskEntity::class, CategoryEntity::class],
    version = 2,
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

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create new table with the correct schema
                database.execSQL(
                    """
            CREATE TABLE CATEGORY_TABLE_NEW (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                imageUri TEXT NOT NULL,
                isPredefined INTEGER NOT NULL,
                tasksCount INTEGER NOT NULL
            )
        """.trimIndent()
                )

                // Copy the data from old table to new table
                database.execSQL(
                    """
            INSERT INTO CATEGORY_TABLE_NEW (id, title, imageUri, isPredefined, tasksCount)
            SELECT id, title, imageUrl, isPredefined, tasksCount FROM CATEGORY_TABLE
        """.trimIndent()
                )

                // Remove the old table
                database.execSQL("DROP TABLE CATEGORY_TABLE")

                // Rename the new table to the old table's name
                database.execSQL("ALTER TABLE CATEGORY_TABLE_NEW RENAME TO CATEGORY_TABLE")
            }
        }


        private fun buildDatabase(context: Context): TudeeDatabase {
            return Room.databaseBuilder(context, TudeeDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}