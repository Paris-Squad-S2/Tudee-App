package com.example.tudeeapp.presentation.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.example.tudeeapp.presentation.design_system.color.TudeeColors
import com.example.tudeeapp.presentation.design_system.color.TudeeLocalColors
import com.example.tudeeapp.presentation.design_system.text_style.LocalTudeeTextStyle
import com.example.tudeeapp.presentation.design_system.text_style.TudeeTextStyle

object Theme {
    val colors: TudeeColors
        @Composable
        @ReadOnlyComposable
        get() = TudeeLocalColors.current

    val textStyle: TudeeTextStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalTudeeTextStyle.current
}