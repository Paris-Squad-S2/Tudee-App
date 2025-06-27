package com.example.tudeeapp.presentation.common.extentions

import com.example.tudeeapp.presentation.screen.home.utils.getToday

fun getCurrentDateString(): String {
    return getToday().date.toString()
}