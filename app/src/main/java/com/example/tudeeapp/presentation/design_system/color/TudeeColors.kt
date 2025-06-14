package com.example.tudeeapp.presentation.design_system.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TudeeColors(
    val primary: Color,
    val secondary: Color,
    val primaryVariant: Color,
    val primaryGradient: PrimaryGradient,
    val text: TudeeTextColors,
    val surfaceColors: SurfaceColors,
    val status: Status,
    val stroke: Color
)

data class TudeeTextColors(
    val title: Color,
    val body: Color,
    val hint: Color,
)
data class PrimaryGradient(
    val colors:List<Color>
)

data class SurfaceColors(
    val surfaceLow: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val onPrimaryColors:OnPrimaryColors,
    val disable: Color,
)

data class Status(
    val pinkAccent: Color,
    val yellowAccent: Color,
    val greenAccent: Color,
    val purpleAccent: Color,
    val error: Color,
    val overlay: Color,
    val emojiTint: Color,
    val yellowVariant: Color,
    val greenVariant: Color,
    val purpleVariant: Color,
    val errorVariant: Color
)

data class OnPrimaryColors(
    val onPrimary: Color,
    val onPrimaryCaption: Color,
    val onPrimaryCard: Color,
    val onPrimaryStroke: Color,
)
val TudeeLocalColors = staticCompositionLocalOf { lightThemeColors }