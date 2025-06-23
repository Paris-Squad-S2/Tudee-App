package com.example.tudeeapp.presentation.utills

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.domain.models.TaskPriority
import com.example.tudeeapp.presentation.mapper.toResDrawables

enum class TaskPriorityUi {
    HIGH,
    MEDIUM,
    LOW;
    val lowercaseName: String
        get() = name.lowercase()
}

data class PriorityStyle(
    val iconRes: Int,
    val backgroundColor: Color
)

@Composable
fun TaskPriorityUi.toStyle(): PriorityStyle {
    return when (this) {
        TaskPriorityUi.HIGH -> PriorityStyle(
            iconRes = R.drawable.ic_flag,
            backgroundColor = Theme.colors.status.pinkAccent
        )
        TaskPriorityUi.MEDIUM -> PriorityStyle(
            iconRes = R.drawable.ic_alert,
            backgroundColor = Theme.colors.status.yellowAccent
        )
        TaskPriorityUi.LOW -> PriorityStyle(
            iconRes = R.drawable.ic_trade_down,
            backgroundColor = Theme.colors.status.greenAccent
        )
    }
}


fun TaskPriority.toUi(): TaskPriorityUi {
    return when (this) {
        TaskPriority.HIGH -> TaskPriorityUi.HIGH
        TaskPriority.MEDIUM -> TaskPriorityUi.MEDIUM
        TaskPriority.LOW -> TaskPriorityUi.LOW
    }
}

@Composable
fun toPainter(isPredefined: Boolean, imageUri: String) =
    if (isPredefined) {
        painterResource(imageUri.toResDrawables())
    } else {
        rememberAsyncImagePainter(imageUri)
    }