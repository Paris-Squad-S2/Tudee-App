package com.example.tudeeapp.presentation.screen.taskDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.PriorityButton
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.design_system.theme.Theme


@Composable
fun TaskDetailsScreen() {

    var isSheetOpen by remember { mutableStateOf(true) }
    TudeeBottomSheet(
        isVisible = isSheetOpen,
        title = "Task details",
        isScrollable = false,
        skipPartiallyExpanded = false,
        onDismiss = { isSheetOpen = true },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.surfaceColors.surface)
                    .padding(horizontal = 16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .padding(top = 12.dp)
                        .background(
                            color = Theme.colors.surfaceColors.surfaceHigh,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_education),
                        contentDescription = "task category icon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center)
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Organize Study Desk",
                    style = Theme.textStyle.title.medium,
                    color = Theme.colors.text.title
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Solve all exercises from page 45 to 50 in the textbook, Solve all exercises from page 45 to 50 in the textbook.",
                    style = Theme.textStyle.body.medium,
                    color = Theme.colors.text.body
                )
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .background(Theme.colors.stroke)
                )
                Row {
                    BoxTaskStatus()
                    PriorityButton(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = "High",
                        icon = painterResource(id = R.drawable.ic_flag),
                        backgroundColor = Theme.colors.status.pinkAccent,
                        onClick = {}
                    )
                }
                Row(modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)) {
                    TudeeButton(
                        onClick = { },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_pencil_edit),
                                null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        variant = ButtonVariant.OutlinedButton,
                    )
                    Spacer(Modifier.width(4.dp))
                    TudeeButton(
                        modifier = Modifier.weight(1f),
                        onClick = { }, text = "Move to done",
                        variant = ButtonVariant.OutlinedButton,
                    )
                }
            }
        },
    )
}

@Composable
private fun BoxTaskStatus() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Theme.colors.status.purpleVariant)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(5.dp)
                    .background(Theme.colors.status.purpleAccent)
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "in progress",
                style = Theme.textStyle.label.small,
                color = Theme.colors.status.purpleAccent
            )
        }
    }
}


@Preview
@Composable
private fun TaskDetailsScreenPreview() {
    TaskDetailsScreen()
}