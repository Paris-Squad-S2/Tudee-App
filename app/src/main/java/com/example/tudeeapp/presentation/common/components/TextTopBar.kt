package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun TextTopBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.surfaceColors.surfaceHigh)
            .statusBarsPadding()
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Text(
            text = "Categories",
            style = Theme.textStyle.title.large,
            color = Theme.colors.text.title
        )
    }
}