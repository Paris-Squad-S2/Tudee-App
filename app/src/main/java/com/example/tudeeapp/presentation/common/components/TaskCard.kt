package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun TaskCard(
    icon: Painter,
    iconColor: Color,
    title: String,
    subtitle: String,
    priorityLabel: String,
    priorityIcon: Painter,
    priorityColor: Color,
    isDated: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(top = 4.dp, bottom = 12.dp, start = 4.dp, end = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "Icon Description",
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Row (
                modifier = Modifier.weight(1f),
                Arrangement.End
            ){
                when {
                    isDated -> DateCard()
                }
                Spacer(modifier= Modifier.width(4.dp))
                PriorityButton(
                    text = priorityLabel,
                    icon = priorityIcon,
                    backgroundColor = priorityColor) { }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )
        {
            Text(
                text = title,
                style = Theme.textStyle.label.large,
                color = Theme.colors.text.body,
                maxLines = 1,
            )
            Text(
                text = subtitle,
                style = Theme.textStyle.label.small,
                color = Theme.colors.text.hint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun DateCard() {
    Box(
        modifier = Modifier.background(
            color = Theme.colors.surfaceColors.surface,
            shape = RoundedCornerShape(50)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_calender),
                contentDescription = "Date",
                tint = Color.Unspecified,
                modifier = Modifier.padding(end = 2.5.dp)
            )
            Text(
                text = "12-03-2025",
                style = Theme.textStyle.label.small,
                color = Theme.colors.text.body,
            )
        }
    }
}

@Preview
@Composable
fun TaskCardPreview() {
    TaskCard(
        icon = painterResource(R.drawable.ic_education),
        iconColor = Color.Unspecified,
        title = "Organize Study Desk",
        subtitle = "Review cell structure and functions for tomorrow...",
        priorityLabel = "Medium",
        priorityIcon = painterResource(R.drawable.ic_alert),
        priorityColor = Theme.colors.status.yellowAccent,
        isDated = true
    )
}
