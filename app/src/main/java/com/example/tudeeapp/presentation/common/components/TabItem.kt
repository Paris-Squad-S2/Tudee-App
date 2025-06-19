package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun TabItem(
    tab: Tab,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedTextColor: Color,
    unselectedTextColor: Color,
    modifier: Modifier = Modifier,
    badgeColor: Color = Theme.colors.surfaceColors.surface,
    indicatorColor: Color = Theme.colors.secondary,
    indicatorHeight: Dp = 4.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.Companion.weight(1f),
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = tab.title,
                    style = Theme.textStyle.label.medium,
                    color = if (isSelected) selectedTextColor else unselectedTextColor
                )
                if (tab.count >= 0 && isSelected) {
                    Box(
                        modifier = Modifier.Companion
                            .clip(CircleShape)
                            .background(badgeColor),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        Text(
                            modifier = Modifier.Companion.padding(
                                horizontal = 4.dp,
                                vertical = 3.dp
                            ),
                            text = tab.count.toString(),
                            style = Theme.textStyle.label.medium,
                            color = Theme.colors.text.body
                        )
                    }
                }
            }
            if (isSelected)
                Box(
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .padding(horizontal = indicatorHeight)
                        .height(4.dp)
                        .background(
                            color = indicatorColor,
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                )
        }
    }
}