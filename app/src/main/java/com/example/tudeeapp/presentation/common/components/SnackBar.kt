package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    text: String,
    isSuccess: Boolean
) {
    val iconContainerColor = if (isSuccess) Theme.colors.status.greenVariant else Theme.colors.status.errorVariant
    val icon = if (isSuccess) painterResource(id = R.drawable.checkmark_badge)
    else painterResource(id = R.drawable.information_diamond)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp), clip = false)
            .background(Theme.colors.surfaceColors.surfaceHigh, RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = iconContainerColor,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = text,
            style = Theme.textStyle.body.medium,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}


@PreviewMultiDevices()
@Composable
private fun SnackBarErrorPreview() {
    BasePreview {
        SnackBar(
            text = "Some error happened",
            isSuccess = false
        )
    }
}