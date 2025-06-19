package com.example.tudeeapp.presentation.screen.task.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.tudeeapp.presentation.design_system.theme.Theme
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme

@Composable
fun DateHeader(
    date: String,
    modifier: Modifier = Modifier,
    onClickPrevious: () -> Unit = {},
    onClickNext: () -> Unit = {},
    onClickPickDate: () -> Unit = {},
) {
    val layoutDirection = LocalLayoutDirection.current
    val rotationAngle = if (layoutDirection == LayoutDirection.Rtl) 180f else 0f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        ArrowContainer(
            painterResource(R.drawable.ic_left_arrow),
            onClickPrevious,
            rotationAngle = rotationAngle
        )
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = date,
                color = Theme.colors.text.body,
                style = Theme.textStyle.label.medium,
                modifier = Modifier.clickable(onClick = onClickPickDate)
            )
            ArrowIcon(
                painterResource(R.drawable.ic_bottom_arrow),
                modifier.padding(start = 8.dp).clickable(onClick = onClickPickDate)
            )
        }
        ArrowContainer(
            painterResource(R.drawable.ic_right_arrow),
            onClickNext,
            rotationAngle = rotationAngle
        )
    }
}

@Composable
fun ArrowContainer(
    icon: Painter,
    onclick: () -> Unit,
    rotationAngle: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .border(
                color = Theme.colors.stroke,
                width = 1.dp,
                shape = CircleShape
            )
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = onclick)
    ) {
        ArrowIcon(icon, modifier.align(Alignment.Center).rotate(rotationAngle))
    }
}

@Composable
private fun ArrowIcon(
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = icon,
        tint = Theme.colors.text.body,
        contentDescription = "Back Icon",
        modifier = modifier
    )
}

@Composable
@PreviewLightDark
fun DateHeaderPreview() {
    TudeeTheme {
        DateHeader("Jun, 2025")
    }
}