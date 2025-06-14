package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun TopAppBar(
    onClickBack: () -> Unit,
    title: String,
    label: String?,
    modifier: Modifier = Modifier,
    withOption: Boolean = false,
    showIndicator: Boolean = false,
    onclickOption: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onClickBack },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = Theme.colors.stroke
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
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
                showIndicator = showIndicator
            )
        }
    }
}

@Composable
fun OptionsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showIndicator: Boolean = false
) {
    Box(modifier = modifier) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_options),
                contentDescription = "Options",
                tint = Theme.colors.stroke
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
            showIndicator = true,
            title = "Tasks",
            label = "32 Task"
        )
    }
}
