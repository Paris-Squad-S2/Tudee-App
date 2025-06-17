package com.example.tudeeapp.presentation.utills

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

enum class TaskPriorityUi {
    HIGH,
    MEDIUM,
    LOW
}

data class PriorityStyle(
    val iconRes: Int,
    val backgroundColor: Color
)

@Composable
fun TaskPriorityUi.toStyle(): PriorityStyle {
    return when (this) {
        TaskPriorityUi.HIGH -> PriorityStyle(
            iconRes = R.drawable.ic_alert,
            backgroundColor = Theme.colors.status.pinkAccent
        )
        TaskPriorityUi.MEDIUM -> PriorityStyle(
            iconRes = R.drawable.ic_flag,
            backgroundColor = Theme.colors.status.yellowAccent
        )
        TaskPriorityUi.LOW -> PriorityStyle(
            iconRes = R.drawable.ic_trade_down,
            backgroundColor = Theme.colors.status.greenAccent
        )
    }
}