package com.example.tudeeapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.tudeeapp.R
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.presentation.design_system.theme.Theme


@Composable
fun TaskPriority.toColor(): Color = when (this) {
    TaskPriority.LOW -> Theme.colors.status.greenAccent
    TaskPriority.MEDIUM -> Theme.colors.status.yellowAccent
    TaskPriority.HIGH -> Theme.colors.status.pinkAccent
}

@Composable
fun TaskPriority.toIcon(): Painter = when (this) {
    TaskPriority.LOW -> painterResource(R.drawable.ic_trade_down)
    TaskPriority.MEDIUM -> painterResource(R.drawable.ic_alert)
    TaskPriority.HIGH -> painterResource(R.drawable.ic_flag)
}
