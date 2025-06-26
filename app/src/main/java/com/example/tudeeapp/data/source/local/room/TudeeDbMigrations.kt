package com.example.tudeeapp.data.source.local.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object TudeeDbMigrations {

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

}