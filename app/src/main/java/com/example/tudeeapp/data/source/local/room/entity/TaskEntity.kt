package com.example.tudeeapp.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "TASK_TABLE")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "date")
    val date: LocalDateTime,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "priority")
    val priority: String,
    @ColumnInfo(name = "categoryId")
    val categoryId: Long,
)
