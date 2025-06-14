package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun DayItem(isSelected: Boolean, dayNumber: String, dayName: String, modifier: Modifier = Modifier) {
    val dayNumberTextColor = if (isSelected) Theme.colors.surfaceColors.onPrimaryColors.onPrimary else Theme.colors.text.body
    val dayNameTextColor = if (isSelected) Theme.colors.surfaceColors.onPrimaryColors.onPrimaryCaption else Theme.colors.text.hint
    val backgroundColor = if (isSelected) Brush.verticalGradient(Theme.colors.primaryGradient.colors)
    else Brush.verticalGradient(listOf(Theme.colors.surfaceColors.surface, Theme.colors.surfaceColors.surface))

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(brush = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp , vertical = 12.dp)
        ) {
            Text(
                text = dayNumber,
                style = Theme.textStyle.title.medium,
                color = dayNumberTextColor
            )
            Text(
                text = dayName,
                modifier = Modifier.padding(top = 2.dp),
                style = Theme.textStyle.body.small,
                color = dayNameTextColor
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun DayItemPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        DayItem(isSelected = true, dayNumber = "12", dayName = "Mon")
        DayItem(isSelected = false, dayNumber = "12", dayName = "Mon")
    }
}