package com.example.tudeeapp.presentation.screen.categoriesForm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.ButtonState
import com.example.tudeeapp.presentation.common.components.ButtonVariant
import com.example.tudeeapp.presentation.common.components.TudeeButton
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.design_system.theme.TudeeTheme
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormUIState

@Composable
fun CategoriesBottomSheetButtons(
    state : CategoryFormUIState,
    onSubmit: ()-> Unit,
    onCancel: ()-> Unit,
    buttonText : String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 20.dp,
                ambientColor = Color(0x14000000),
                spotColor = Color(0x14000000)
            )
            .background(
                Theme.colors.surfaceColors.surfaceHigh
            )
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            TudeeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                onClick = onSubmit,
                text = buttonText,
                variant = ButtonVariant.FilledButton,
                state = if (state.isFormValid) ButtonState.Normal else ButtonState.Disabled,
            )
            TudeeButton(
                onClick = onCancel,
                text = stringResource(id = R.string.cancel),
                variant = ButtonVariant.OutlinedButton,
                state = ButtonState.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}

@Composable
@Preview
fun CategoriesBottomSheetButtonsPreview() {
    TudeeTheme {
        CategoriesBottomSheetButtons(
            state = CategoryFormUIState(),
            onSubmit = {},
            onCancel = {},
            buttonText = stringResource(id = R.string.add)
        )
    }
}