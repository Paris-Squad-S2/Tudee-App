package com.example.tudeeapp.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CATEGORY_TABLE")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long =0,
    val title: String,
    val imageUrl: String,
    val isPredefined: Boolean,
    val tasksCount:Int
)
