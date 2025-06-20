package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun TopAppBar(
    onClickBack: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    withOption: Boolean = false,
    showIndicator: Boolean = false,
    onclickOption: () -> Unit = {},
    iconButton: ImageVector = ImageVector.vectorResource(R.drawable.ic_options),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke,
                    shape = CircleShape
                ).clickable { onClickBack() }

        ){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.large,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            label?.let {
                Text(
                    text = label,
                    style = Theme.textStyle.label.small,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (withOption) {
            OptionsButton(
                onClick = onclickOption,
                showIndicator = showIndicator,
                iconButton = iconButton
            )
        }
    }
}

@Composable
private fun OptionsButton(
    onClick: () -> Unit,
    iconButton: ImageVector,
    modifier: Modifier = Modifier,
    showIndicator: Boolean = false
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = Theme.colors.stroke,
                    shape = CircleShape
                ).clickable { onClick() }

        ){
            Icon(
                imageVector = iconButton,
                contentDescription = "Options",
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
        }


        if (showIndicator) {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .size(8.dp)
                    .align(Alignment.TopStart)
                    .background(Theme.colors.secondary, CircleShape),
            )
        }
    }
}

@PreviewMultiDevices
@Composable
private fun TopAppBarPreview() {
    BasePreview {
        TopAppBar(
            onClickBack = {},
            onclickOption = {},
            modifier = Modifier,
            withOption = true,
            showIndicator = false,
            title = "Tasks",
            label = "32 Task"
        )
    }
}