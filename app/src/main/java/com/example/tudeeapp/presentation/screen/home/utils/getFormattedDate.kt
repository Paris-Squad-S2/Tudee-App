package com.example.tudeeapp.presentation.screen.home.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.DateFormatSymbols
import java.util.Locale

fun getLocalizedToday(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val monthNames = DateFormatSymbols(Locale.getDefault()).shortMonths
    val monthName = monthNames[now.monthNumber - 1]
    return "${now.dayOfMonth} $monthName ${now.year}"
}
