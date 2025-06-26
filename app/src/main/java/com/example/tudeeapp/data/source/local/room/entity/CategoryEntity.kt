package com.example.tudeeapp.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CATEGORY_TABLE")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long =0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "imageUri")
    val imageUri: String,
    @ColumnInfo(name = "isPredefined")
    val isPredefined: Boolean,
    @ColumnInfo(name = "tasksCount")
    val tasksCount:Int
)
