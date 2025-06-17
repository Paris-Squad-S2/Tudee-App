package com.example.tudeeapp.presentation.bottomSheets.addEditTask

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.AddEditStickButtons
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.AddEditTextFields
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.CategoryGrid
import com.example.tudeeapp.presentation.bottomSheets.addEditTask.components.PriorityRow
import com.example.tudeeapp.presentation.common.components.TudeeBottomSheet
import com.example.tudeeapp.presentation.common.extentions.BasePreview

@Composable
fun AddEditTaskBottomSheet(
    modifier: Modifier = Modifier,
    bottomTitle: String,
) {
    TudeeBottomSheet(
        isVisible = true,
        title = "Add Task",
        onDismiss = {},
        modifier = modifier,
        isScrollable = true,
        skipPartiallyExpanded = true,
        stickyBottomContent = {
            AddEditStickButtons(
                buttonTitle = bottomTitle,
                onClick = {},
                onClickCancel = {},
                isCompleted = false,
            )
        },
    ) {
        Column {
            AddEditTextFields()
            PriorityRow(
                onPriorityHeightClicked = TODO(),
                onPriorityMediumClicked = TODO(),
                onPriorityLowClicked = TODO(),
                modifier = TODO()
            )
            CategoryGrid(
                categories = TODO(),
                modifier = TODO()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AddEditTaskBottomSheetPreview() {
    BasePreview {
        AddEditTaskBottomSheet(bottomTitle = "Add")
    }
}