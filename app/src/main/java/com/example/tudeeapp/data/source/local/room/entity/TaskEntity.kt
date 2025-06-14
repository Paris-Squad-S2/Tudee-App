package com.example.tudeeapp.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val date: Int,
    val status: String,
    val priority: String,
    val categoryId: Long
)
