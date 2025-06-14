package com.example.tudeeapp.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val imageUri: String,
    val isPredefined: Boolean
)
