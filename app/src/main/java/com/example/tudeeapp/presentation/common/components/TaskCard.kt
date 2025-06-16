package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun TaskItemWithSwipe(
    icon: Painter,
    title: String,
    date: String,
    subtitle: String,
    priorityLabel: String,
    priorityIcon: Painter,
    priorityColor: Color,
    isDated: Boolean,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    onClickPriority: () -> Unit = {},
    onClickDate: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    SwipeToDeleteTaskItem(
        modifier = modifier,
        onDelete = onDelete,
    ) {
        TaskCard(
            icon = icon,
            title = title,
            date = date,
            subtitle = subtitle,
            priorityLabel = priorityLabel,
            priorityIcon = priorityIcon,
            priorityColor = priorityColor,
            isDated = isDated,
            iconColor = iconColor,
            onClickPriority = onClickPriority,
            onClickDate = onClickDate
        )
    }
}

@Composable
private fun TaskCard(
    icon: Painter,
    title: String,
    date: String,
    subtitle: String,
    priorityLabel: String,
    priorityIcon: Painter,
    priorityColor: Color,
    isDated: Boolean,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    onClickPriority: () -> Unit = {},
    onClickDate: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Theme.colors.surfaceColors.surfaceHigh,
                shape = RoundedCornerShape(12.dp)
            )
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
            Row(
                modifier = Modifier.weight(1f),
                Arrangement.End
            ) {
                when {
                    isDated -> DateCard(date, onClick = onClickDate)
                }
                Spacer(modifier = Modifier.width(4.dp))
                PriorityButton(
                    text = priorityLabel,
                    icon = priorityIcon,
                    backgroundColor = priorityColor,
                    onClick = onClickPriority
                )
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
private fun DateCard(date: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = Theme.colors.surfaceColors.surface,
                shape = RoundedCornerShape(50),
            )
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_calender),
                contentDescription = "Date",
                tint = Theme.colors.text.body,
                modifier = Modifier.padding(end = 2.5.dp)
            )
            Text(
                text = date,
                style = Theme.textStyle.label.small,
                color = Theme.colors.text.body,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteTaskItem(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    taskCardContent: @Composable () -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }

    if (isDismissed) {
        LaunchedEffect(Unit) {
            onDelete()
            isDismissed = false
        }
    }

    SwipeToDismissBox(
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(Theme.colors.status.errorVariant)
                    .padding(end = 12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Delete",
                    tint = Theme.colors.status.error,
                    modifier = Modifier.size(32.dp)
                )

            }
        },
        content = {
            taskCardContent()
        },
        state = rememberSwipeToDismissBoxState(
            confirmValueChange = { dismissValue ->
                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                    isDismissed = true
                    true
                } else {
                    false
                }
            }
        )
    )
}


@Preview
@Composable
private fun TaskItemWithSwipePreview() {
    TaskItemWithSwipe(
        icon = rememberVectorPainter(Icons.Default.Add),
        title = "Organize Study Desk",
        date = "2023-10-15",
        subtitle = "Review cell structure and functions for tomorrow...",
        priorityLabel = "Medium",
        priorityIcon = painterResource(R.drawable.ic_alert),
        priorityColor = Theme.colors.status.yellowAccent,
        isDated = true
    )

}
