package com.example.tudeeapp.presentation.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.example.tudeeapp.presentation.design_system.color.LocalTudeeColors
import com.example.tudeeapp.presentation.design_system.color.darkThemeColors
import com.example.tudeeapp.presentation.design_system.color.lightThemeColors
import com.example.tudeeapp.presentation.design_system.text_style.LocalTudeeTextStyle
import com.example.tudeeapp.presentation.design_system.text_style.generateTudeeTextStyle

@Composable
fun TudeeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colors = if (isDarkTheme) darkThemeColors else lightThemeColors
    val coloredTextStyle = remember(colors) { generateTudeeTextStyle(colors) }

    CompositionLocalProvider(
        LocalTudeeColors provides colors,
        LocalTudeeTextStyle provides coloredTextStyle
    ){
        content()
    }
}