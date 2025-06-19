package com.example.tudeeapp.presentation.common.extentions

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateString(): String {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
}