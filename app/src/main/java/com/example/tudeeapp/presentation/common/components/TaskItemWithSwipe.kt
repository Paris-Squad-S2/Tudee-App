package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme
import kotlin.math.roundToInt

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

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
private fun SwipeToDeleteTaskItem(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    maxSwipeDistance: Dp = 56.dp,
    taskCardContent: @Composable () -> Unit
) {
    val maxSwipePx = with(LocalDensity.current) { maxSwipeDistance.toPx() }
    val swipeableState = rememberSwipeableState(initialValue = SwipeState.DEFAULT)

    val anchors = mapOf(0f to SwipeState.DEFAULT, -maxSwipePx to SwipeState.SWIPED)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                reverseDirection = false,
                resistance = null // Optional: remove resistance to make swipe natural
            )
    ) {
        // Background (Delete Icon)
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
                .background(Theme.colors.status.errorVariant)
                .padding(end = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_delete),
                contentDescription = "Delete",
                tint = Theme.colors.status.error,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onDelete
                    }
            )
        }

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = swipeableState.offset.value.roundToInt()
                            .coerceAtLeast(-maxSwipePx.toInt()),
                        y = 0
                    )
                }
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            taskCardContent()
        }
    }

    /*LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == SwipeState.SWIPED) {
            onDelete
            swipeableState.snapTo(SwipeState.DEFAULT)
        }
    }*/
}

private enum class SwipeState {
    DEFAULT,
    SWIPED
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