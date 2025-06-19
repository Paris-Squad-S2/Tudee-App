package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
            .background(
                Theme.colors.surfaceColors.surface,
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Are you sure to continue?",
                style = Theme.textStyle.body.large,
                color = Theme.colors.text.body,
            )
            Image(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 24.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(107.dp)
                    .height(100.dp),
                painter = painterResource(R.drawable.tudee_img),
                contentDescription = "tudee image",


                )
        }

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
            )
        }
    }
}

@Preview
@Composable
fun DeleteTaskConfirmationBoxPreview() {
    DeleteTaskConfirmationBox()
}