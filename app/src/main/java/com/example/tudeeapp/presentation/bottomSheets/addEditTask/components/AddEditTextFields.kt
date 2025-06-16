package com.example.tudeeapp.presentation.bottomSheets.addEditTask.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.TextField
import com.example.tudeeapp.presentation.common.extentions.BasePreview

@Composable
fun AddEditTestFields(
    modifier: Modifier = Modifier,
    title: String = "Task Title",
    onValueChange: (String) -> Unit = {},
    description: String = "Description",
) {
    //To ask for
    var value by remember { mutableStateOf("") }
    var multiLineText by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier.padding(top = 12.dp),
            value = value,
            onValueChange = onValueChange,
            placeholder = title,
            leadingIcon = R.drawable.ic_unselected_tasks,
        )

        TextField(
            modifier = Modifier.padding(vertical = 16.dp),
            value = multiLineText,
            onValueChange = { multiLineText = it },
            placeholder = description,
            singleLine = false,
            maxLines = 5,
            leadingIcon = null
        )
        TextField(
            modifier = Modifier.padding(vertical = 16.dp),
            value = multiLineText,
            onValueChange = { multiLineText = it },
            placeholder = "22-6-2025",
            leadingIcon = R.drawable.ic_calendar_add
        )
    }

}

@PreviewLightDark
@Composable
private fun AddEditTaskFieldsPreview() {
    BasePreview {
        AddEditTestFields()
    }
}