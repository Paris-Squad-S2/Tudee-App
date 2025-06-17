package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.tudeeapp.R
import com.example.tudeeapp.data.mapper.DataConstant.toResDrawables
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun CategoryItem(
    icon: String,
    label: String,
    iconColor: Color,
    isPredefined: Boolean,
    count: Int? = null,
    isSelected: Boolean = true,
    onClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(78.dp)
                .background(
                    color = Theme.colors.surfaceColors.surfaceHigh, shape = CircleShape
                )
        ) {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClick)
            )
            when {
                isSelected -> SelectedBadge(Modifier.align(Alignment.TopEnd))
                count != null -> NumBadge(count, Modifier.align(Alignment.TopEnd))
            }

            if (isPredefined) {
                Icon(
                    painter = painterResource(icon.toResDrawables()),
                    contentDescription = "$label Icon",
                    tint = iconColor,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            } else {
                AsyncImage(
                    model = icon,
                    contentDescription = "$label Icon",
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Text(
            text = label, style = Theme.textStyle.label.small, color = Theme.colors.text.body
        )
    }
}

@Composable
private fun NumBadge(
    count: Int?,
    modifier: Modifier,
) {
    count?.let {
        Box(
            modifier = modifier
                .background(
                    color = Theme.colors.surfaceColors.surfaceLow,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(horizontal = 10.5.dp, vertical = 2.dp)
        ) {

            Text(
                text = it.toString(), color = Theme.colors.text.hint, fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SelectedBadge(modifier: Modifier) {
    Box(
        modifier = modifier
            .background(
                color = Theme.colors.status.greenAccent, shape = CircleShape
            )
            .size(20.dp), contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_checkmark),
            contentDescription = "Check Mark Icon",
            tint = Theme.colors.surfaceColors.surfaceHigh
        )
    }
}

@PreviewMultiDevices
@Composable
private fun PreviewCategoryItems() {
    BasePreview {
        CategoryItem(
            icon = "R.drawable.education",
            label = "Education",
            iconColor = Color.Unspecified,
            isPredefined = true,
            count = 23
        )
    }
}