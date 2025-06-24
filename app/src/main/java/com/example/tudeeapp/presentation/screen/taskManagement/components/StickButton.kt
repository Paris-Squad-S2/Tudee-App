package com.example.tudeeapp.presentation.screen.taskManagement.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonState
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.common.extentions.PreviewMultiDevices
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun TaskManagementButtons(
    isEditMode: Boolean,
    isActionButtonDisabled: Boolean,
    onClickActionButton: () -> Unit,
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Theme.colors.surfaceColors.surfaceHigh)
            .padding(top = 12.dp, bottom = 40.dp, end = 12.dp, start = 16.dp)
    ) {
        TudeeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            onClick = { onClickActionButton() },
            text = if (isEditMode) stringResource(R.string.save) else stringResource(R.string.add),
            variant = ButtonVariant.FilledButton,
            state = when {
                isLoading -> ButtonState.Loading
                isActionButtonDisabled -> ButtonState.Disabled
                else -> ButtonState.Normal
            },
        )
        TudeeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            onClick = { onClickCancel() },
            text = stringResource(R.string.cancel),
            variant = ButtonVariant.OutlinedButton,
            state = ButtonState.Normal,
        )
    }
}

@PreviewMultiDevices
@Composable
fun AddEditUnStickButtonsPreview() {
    BasePreview {
        TaskManagementButtons(
            onClickActionButton = {},
            onClickCancel = {},
            isEditMode = true,
            isActionButtonDisabled = false,
        )
    }
}
