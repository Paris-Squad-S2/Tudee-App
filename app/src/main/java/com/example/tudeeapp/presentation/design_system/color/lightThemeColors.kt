package com.example.tudeeapp.presentation.design_system.color

import androidx.compose.ui.graphics.Color

val lightThemeColors = TudeeColors(
    primary = Color(0xFF49BAF2),
    secondary = Color(0xFFF49061),
    primaryVariant = Color(0xFFEFF9FE),
    primaryGradient = PrimaryGradient(listOf(Color(0xFF49BAF2), Color(0xFF3A9CCD))),
    stroke = Color(0x1F1F1F1F),
    text = TudeeTextColors(
        title = Color(0xDE1F1F1F),
        body = Color(0x991F1F1F),
        hint = Color(0x611F1F1F),
        disable = Color(0x1F1F1F1F)
    ),
    surfaceColors = SurfaceColors(
        surfaceLow = Color(0xFFF0F0F0),
        surface = Color(0xFFF9F9F9),
        surfaceHigh = Color(0xFFFFFFFF),
        onPrimaryColors = OnPrimaryColors(
            onPrimary = Color(0xDEFFFFFF),
            onPrimaryCaption = Color(0xB3FFFFFF),
            onPrimaryCard = Color(0x29FFFFFF),
            onPrimaryStroke = Color(0x99FFFFFF),
        ),
        disable = Color(0xFFE8EBED),
    ),
    status = Status(
        pinkAccent = Color(0xFFF4869A),
        yellowAccent = Color(0xFFF2C849),
        greenAccent = Color(0xFF76C499),
        purpleAccent = Color(0xFF9887F5),
        error = Color(0xFFE55C5C),
        overlay = Color(0x5249BAF2),
        emojiTint = Color(0xDE1F1F1F),
        yellowVariant = Color(0xFFF7F2E4),
        greenVariant = Color(0xFFE4F2EA),
        purpleVariant = Color(0xFFEEEDF7),
        errorVariant = Color(0xFFFCE8E8)
    )
)