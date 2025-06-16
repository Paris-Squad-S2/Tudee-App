package com.example.tudeeapp.domain.models

import kotlin.random.Random

data class Category(
    val id: Long = Random.nextLong(1L, Long.MAX_VALUE),
    val title: String,
    val imageUrl : String,
    val isPredefined: Boolean = false,
    val tasksCount:Int=0,
)
