package com.example.tudeeapp.presentation.screen.taskManagement.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.components.TextField
import com.example.tudeeapp.presentation.common.extentions.BasePreview

@Composable
fun TaskManagementTextFields(
    title: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateClicked: () -> Unit,
    description: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier.padding(top = 12.dp),
            value = title,
            onValueChange = onTitleChange,
            placeholder = stringResource(R.string.task_title),
            leadingIcon = R.drawable.ic_unselected_tasks,
        )
        TextField(
            modifier = Modifier.padding(top = 16.dp),
            value = description,
            onValueChange = onDescriptionChange,
            placeholder = stringResource(R.string.description),
            singleLine = false,
            leadingIcon = null
        )
        TextField(
            modifier = Modifier
                .padding(top = 16.dp),
            value = date,
            enabled = false,
            onClick =  {onDateClicked()},
            onValueChange = { },
            placeholder = date,
            leadingIcon = R.drawable.ic_calendar_add,
        )
    }
}

@PreviewLightDark
@Composable
private fun AddEditTaskFieldsPreview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    BasePreview {
        TaskManagementTextFields(
            title = title,
            onTitleChange = { title = it },
            description = description,
            onDescriptionChange = { description = it },
            onDateClicked = { },
            date = "2025-06-17",
        )
    }
}