package com.example.tudeeapp.presentation.bottomSheets.addEditTask.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.PriorityButton
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun PriorityRow(
    onPriorityHeightClicked: () -> Unit,
    onPriorityMediumClicked: () -> Unit,
    onPriorityLowClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.priority),
            style = Theme.textStyle.title.medium,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PriorityButton(
                text = "Height",
                icon = painterResource(R.drawable.ic_flag),
                backgroundColor = Theme.colors.surfaceColors.surfaceLow,
                contentColor = Theme.colors.text.hint,
                onClick = {
                    onPriorityHeightClicked()
                }
            )
            PriorityButton(
                text = "Medium",
                icon = painterResource(R.drawable.ic_worning),
                backgroundColor = Theme.colors.surfaceColors.surfaceLow,
                contentColor = Theme.colors.text.hint,
                onClick = {
                    onPriorityMediumClicked()
                }
            )
            PriorityButton(
                text = "Low",
                icon = painterResource(R.drawable.ic_low),
                backgroundColor = Theme.colors.surfaceColors.surfaceLow,
                contentColor = Theme.colors.text.hint,
                onClick = {
                    onPriorityLowClicked()
                }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
private fun PriorityRowPreview() {
    BasePreview {
        PriorityRow(
            onPriorityHeightClicked = {},
            onPriorityMediumClicked = {},
            onPriorityLowClicked = {}
        )
    }
}