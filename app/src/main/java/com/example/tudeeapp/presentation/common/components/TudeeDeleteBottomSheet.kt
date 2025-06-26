package com.example.tudeeapp.presentation.common.components

import androidx.compose.runtime.Composable
import com.example.tudeeapp.R

@Composable
fun TudeeDeleteBottomSheet(
    showBottomSheet: Boolean,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    TudeeBottomSheet(
        showSheet = showBottomSheet,
        title = title,
        onDismiss = onDismiss
    ) {
        ConfirmationDialogBox(
            title = R.string.are_you_sure_to_continue,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}