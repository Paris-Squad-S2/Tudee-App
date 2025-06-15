package com.example.tudeeapp.domain.models

data class Category(
    val id: Long=0L,
    val title: String,
    val imageUrl : String,
    val isPredefined: Boolean = false,
    val tasksCount:Int=0,
)
