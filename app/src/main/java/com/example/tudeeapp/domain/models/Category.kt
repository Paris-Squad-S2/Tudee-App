package com.example.tudeeapp.domain.models

data class Category(
    val id: Long =0,
    val title: String,
    val imageUri : String,
    val isPredefined: Boolean = false,
    val tasksCount:Int=0,
)
