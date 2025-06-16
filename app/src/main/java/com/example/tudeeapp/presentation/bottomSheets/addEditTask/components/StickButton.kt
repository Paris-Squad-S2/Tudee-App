package com.example.tudeeapp.presentation.bottomSheets.addEditTask.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.common.components.ButtonState
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme

@Composable
fun AddEditStickButtons(
    modifier: Modifier = Modifier,
    buttonTitle: String,
    onClick: () -> Unit,
    onClickCancel: () -> Unit,
    isCompleted: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Theme.colors.surfaceColors.surfaceHigh)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        TudeeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            onClick = { onClick },
            text = buttonTitle,
            variant = ButtonVariant.FilledButton,
            state = if (isCompleted) ButtonState.Normal else ButtonState.Disabled,
        )
        TudeeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            onClick = { onClickCancel },
            text = "Cancel",
            variant = ButtonVariant.OutlinedButton,
            state = ButtonState.Normal,
            contentColor = Theme.colors.primary,
        )
    }
}


@PreviewLightDark
@Composable
fun AddEditUnStickButtonsPreview(modifier: Modifier = Modifier) {
    BasePreview {
        AddEditStickButtons(
            modifier = modifier,
            buttonTitle = "Add",
            onClick = {},
            onClickCancel = {},
        )
    }
}