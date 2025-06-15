package com.example.tudeeapp.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TASK_TABLE")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "date")
    val date: String,
    val status: String,
    val priority: String,
    val categoryId: Long,
)
