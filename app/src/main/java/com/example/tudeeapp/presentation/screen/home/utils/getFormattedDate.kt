package com.example.tudeeapp.presentation.screen.home.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.presentation.design_system.theme.Theme
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.DateFormatSymbols
import java.util.Locale

fun getToday(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun getLocalizedToday(): String {
    val now = getToday()
    val monthNames = DateFormatSymbols(Locale.getDefault()).shortMonths
    val monthName = monthNames[now.monthNumber - 1]
    return "${now.dayOfMonth} $monthName ${now.year}"
}

@Composable
fun getPriorityColor(priority: TaskPriority): Color {
    return when (priority) {
        TaskPriority.LOW -> Theme.colors.status.greenAccent
        TaskPriority.MEDIUM -> Theme.colors.status.yellowAccent
        TaskPriority.HIGH -> Theme.colors.status.pinkAccent
    }
}

