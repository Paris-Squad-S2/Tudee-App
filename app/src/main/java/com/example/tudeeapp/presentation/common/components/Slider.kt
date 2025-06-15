package com.example.tudeeapp.presentation.common.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun Slider(
    title: String,
    description: String,
    image: Painter,
    modifier: Modifier = Modifier,
    titleIcon: Painter
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = Theme.textStyle.title.small
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = titleIcon,
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = Theme.textStyle.body.small,
                color = Theme.colors.text.body
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .height(92.dp)
                .width(76.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .offset(y = 6.dp)
                    .clip(CircleShape)
                    .background(color = Theme.colors.primary.copy(alpha = 0.16f))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 12.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = image,
                    contentDescription = "robot",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    }
}

@PreviewMultiDevices
@Composable
private fun SliderPreview() {
    BasePreview {
        Slider(
            title = "Stay working!",
            description = "You've completed 3 out of 10 tasks Keep going!",
            image = painterResource(id = R.drawable.ropot1),
            titleIcon = painterResource(id = R.drawable.good)
        )
    }
}
