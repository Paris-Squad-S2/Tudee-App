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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.utills.toLocalizedString

@Composable
fun TabItem(
    tab: Tab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = tab.title,
                    style = Theme.textStyle.label.medium,
                    color = if (isSelected) Theme.colors.text.title else Theme.colors.text.hint
                )
                if (tab.count >= 0 && isSelected) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Theme.colors.surfaceColors.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                horizontal = 4.dp,
                                vertical = 3.dp
                            ),
                            text = tab.count.toLocalizedString(),
                            style = Theme.textStyle.label.medium,
                            color = Theme.colors.text.body
                        )
                    }
                }
            }
            if (isSelected)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .height(4.dp)
                        .background(
                            color = Theme.colors.secondary,
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                )
        }
    }
}