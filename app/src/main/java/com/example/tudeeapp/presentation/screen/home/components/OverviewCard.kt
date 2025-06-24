package com.example.tudeeapp.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun OverviewCard(
    modifier: Modifier,
    color: Color,
    painter: Painter,
    stat: Int,
    label: String,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 20.dp))
            .background(color = color)
    ) {
        Icon(
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopEnd)
                .offset(x = 39.dp, y = (-39).dp),
            painter = painterResource(R.drawable.ic_overview_card_background),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Column(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, bottom = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x1FFFFFFF),
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .size(40.dp)
                    .background(color = Color(0x3DFFFFFF), shape = RoundedCornerShape(size = 12.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stat.toString(),
                style = Theme.textStyle.headline.medium,
                color = Theme.colors.surfaceColors.onPrimaryColors.onPrimary
            )
            Text(
                text = label,
                style = Theme.textStyle.label.small,
                color = Theme.colors.surfaceColors.onPrimaryColors.onPrimaryCaption
            )
        }
    }
}

@Composable
@Preview
fun OverviewCardPreview(){
    OverviewCard(
        modifier = Modifier,
        color = Theme.colors.status.purpleAccent,
        painter = painterResource(id = R.drawable.ic_to_do),
        stat = 1,
        label = "To-Do",
    )
}