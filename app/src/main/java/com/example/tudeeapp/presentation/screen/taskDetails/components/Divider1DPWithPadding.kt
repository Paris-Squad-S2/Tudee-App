package com.example.tudeeapp.presentation.screen.taskDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun Divider1DPWithPadding() {
    HorizontalDivider(
        modifier = Modifier
            .padding(vertical = 12.dp),
        thickness = 1.dp,
        color = Theme.colors.stroke
    )
}