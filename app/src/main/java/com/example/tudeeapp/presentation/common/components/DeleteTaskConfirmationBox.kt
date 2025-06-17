package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun DeleteTaskConfirmationBox(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}

) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(378.dp)
            .background(
                Theme.colors.surfaceColors.surface,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_drag_handle),
            contentDescription = "Icon Description",
            tint = Theme.colors.text.body,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(32.dp)
                .height(4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clickable(
                    onClick = onConfirm
                )
        ) {
            Text(
                text = "Delete task",
                style = Theme.textStyle.title.large,
                color = Theme.colors.text.title,
            )
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = "Are you sure to continue?",
                style = Theme.textStyle.body.large,
                color = Theme.colors.text.body,
            )
            Image(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(107.dp)
                    .height(100.dp),
                painter = painterResource(R.drawable.tudee_img),
                contentDescription = "tudee image",


                )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.surfaceColors.surfaceHigh)
                .padding(horizontal = 16.dp),
        ) {
            TudeeButton(
                onClick = onConfirm,
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 6.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                text = "Delete",
                isNegative = true,
                variant = ButtonVariant.FilledButton,
                contentColor = Theme.colors.status.error,

                )
            TudeeButton(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(bottom = 12.dp, top = 6.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                text = "Cancel",
                variant = ButtonVariant.OutlinedButton,
                contentColor = Theme.colors.status.overlay,
            )
        }
    }
}

@Preview
@Composable
fun DeleteTaskConfirmationBoxPreview() {
    DeleteTaskConfirmationBox()
}