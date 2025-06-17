package com.example.tudeeapp.presentation.screen.categoriesForm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.presentation.design_system.theme.Theme
import com.example.tudeeapp.presentation.screen.categoriesForm.CategoryFormState

@Composable
fun CategoriesBottomSheetButtons(
    state : CategoryFormState,
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
            Button(
                onClick = onSubmit,
                enabled = state.isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isFormValid)
                        Theme.colors.primary
                    else
                        Theme.colors.text.disable
                )
            ) {
                Text(buttonText, style = Theme.textStyle.label.large)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(
                        width = 1.dp,
                        color = Theme.colors.stroke,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(100.dp)
                    )
            ) {
                Text(
                    "Cancel",
                    style = Theme.textStyle.label.large,
                    color = Theme.colors.primary
                )
            }
        }
    }
}