package com.example.tudeeapp.presentation.design_system.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class TudeeColors(
    val primary: Color,
    val secondary: Color,
    val primaryVarient: Color,
//    val PrimaryGradient: Color = Color(0xFFF49061)
    val text: TudeeTextColors
)

data class TudeeTextColors(
    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color
)

val TudeeLocalColors = staticCompositionLocalOf { lightThemeColors }