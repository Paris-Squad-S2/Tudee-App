package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun EmptyTasksSection(
    title: String,
    description: String = stringResource(R.string.tap_the_button_to_add_your_first_one),
    modifier: Modifier
) {
    Box(
        modifier = modifier,
    ) {

        Box(
            modifier = Modifier
                .size(144.dp)
                .align(Alignment.CenterEnd)
        ) {

            Image(
                painter = painterResource(R.drawable.img_task_card_ropo_background),
                contentDescription = "ropo",
                modifier = Modifier
                    .size(136.dp)
                    .align(Alignment.TopEnd)
            )

            Image(
                painter = painterResource(R.drawable.img_task_card_ropo_container),
                contentDescription = "ropo",
                modifier = Modifier
                    .size(144.dp)
                    .offset(x = (-4).dp, y = (-9).dp)
            )

            Icon(
                painter = painterResource(R.drawable.ic_task_card_dots),
                contentDescription = "ropo2",
                modifier = Modifier
                    .size(54.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-2).dp, y = (10).dp),
                tint = Theme.colors.surfaceColors.surfaceHigh
            )

            Image(
                painter = painterResource(R.drawable.img_robot4),
                contentDescription = "ropo4",
                modifier = Modifier
                    .width(107.dp)
                    .height(100.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 0.dp, y = (-11).dp)

            )
        }
        Column(
            modifier = Modifier
                .padding(bottom = 60.dp,end = 120.dp)
                .offset(x = 0.dp, y = (-30).dp)
                .background(
                    color = Theme.colors.surfaceColors.surfaceHigh,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 2.dp
                    )
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.small,
                color = Theme.colors.text.body,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = description,
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.hint,
                maxLines = 2
            )
        }

    }
}