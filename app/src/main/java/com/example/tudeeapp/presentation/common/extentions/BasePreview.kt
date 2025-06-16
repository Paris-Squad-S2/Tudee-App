package com.example.tudeeapp.presentation.common.extentions

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme

@Composable
fun BasePreview( modifier: Modifier = Modifier , content: @Composable () -> Unit) {
    TudeeTheme {
        Surface(modifier = modifier,color = Theme.colors.surfaceColors.surface) {
            content()
        }
    }
}