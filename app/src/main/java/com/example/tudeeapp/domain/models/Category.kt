package com.example.tudeeapp.domain.models

data class Category(
    val id: Long,
    val title: String,
    val imageUri : String,
    val isPredefined: Boolean
)
