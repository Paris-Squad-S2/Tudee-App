package com.example.tudeeapp.presentation.screen.taskDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun CategoryIcon(painter: Painter) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .size(56.dp)
            .clip(CircleShape)
            .background(color = Theme.colors.surfaceColors.surfaceHigh, shape = CircleShape)
    ) {
        Icon(
            painter = painter,
            contentDescription = "task category icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)

                .align(Alignment.Center)
        )
    }
}
