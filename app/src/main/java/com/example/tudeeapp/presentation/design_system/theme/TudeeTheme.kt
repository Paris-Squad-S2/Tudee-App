package com.example.tudeeapp.presentation.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.tudeeapp.presentation.design_system.color.TudeeLocalColors
import com.example.tudeeapp.presentation.design_system.color.darkThemeColors
import com.example.tudeeapp.presentation.design_system.color.lightThemeColors
import com.example.tudeeapp.presentation.design_system.text_style.LocalTudeeTextStyle
import com.example.tudeeapp.presentation.design_system.text_style.defaultTextStyle

@Composable
fun TudeeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val theme = if (isDarkTheme) darkThemeColors else lightThemeColors
    CompositionLocalProvider(
        TudeeLocalColors provides theme,
        LocalTudeeTextStyle provides defaultTextStyle
    ){
        content()
    }
}