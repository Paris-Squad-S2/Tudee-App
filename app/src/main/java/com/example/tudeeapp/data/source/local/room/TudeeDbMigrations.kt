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

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL("ALTER TABLE TASK_TABLE ADD COLUMN date_new TEXT")

            database.execSQL("UPDATE TASK_TABLE SET date_new = date")

            database.execSQL("""
            CREATE TABLE TASK_TABLE_NEW (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                date TEXT NOT NULL,
                status TEXT NOT NULL,
                priority TEXT NOT NULL,
                categoryId INTEGER NOT NULL
            )
        """.trimIndent())


            database.execSQL("""
            INSERT INTO TASK_TABLE_NEW (id, title, description, date, status, priority, categoryId)
            SELECT id, title, description, date_new, status, priority, categoryId
            FROM TASK_TABLE
        """.trimIndent())

            database.execSQL("DROP TABLE TASK_TABLE")
            database.execSQL("ALTER TABLE TASK_TABLE_NEW RENAME TO TASK_TABLE")
        }
    }

}