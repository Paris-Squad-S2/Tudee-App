package com.example.tudeeapp.presentation.screen.task.components

import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R

@Composable
fun DateHeader(
    date: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically

    ) {
        ArrowContainer( rotationDegree = 0f)
        Row (
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = date,
//                color = Theme.colors.text.body,
//                style = Theme.textStyle.label.medium
            )
            ArrowIcon(rotationDegree = (-90f), modifier.padding(start = 8.dp))
        }
        ArrowContainer(rotationDegree = 180F)
    }
}

@Composable
fun ArrowContainer(
    rotationDegree: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .border(
                color = Color.Unspecified,
//                color = Theme.colors.stroke,
                width = 1.dp,
                shape = CircleShape)
            .clip(RoundedCornerShape(100.dp))
    ) {
        ArrowIcon(rotationDegree, modifier.align(Alignment.Center))
    }
}

@Composable
private fun ArrowIcon(
    rotationDegree: Float,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.ic_left_arrow),
//        tint = Theme.colors.text.body,
        contentDescription = "Back Icon",
        modifier = modifier.rotate(rotationDegree)
    )
}

//@Composable
//@PreviewLightDark
//fun DateHeaderPreview() {
//    TudeeTheme {
//        Surface(color = Theme.colors.surfaceColors.surface)
//        {
//            DateHeader("Jun, 2025")
//        }
//    }
//}