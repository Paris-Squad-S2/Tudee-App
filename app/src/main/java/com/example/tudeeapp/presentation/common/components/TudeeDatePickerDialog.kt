@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudeeapp.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudeeapp.R
import com.example.tudeeapp.presentation.common.extentions.BasePreview
import com.example.tudeeapp.presentation.design_system.theme.Theme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@Composable
fun TudeeDatePickerDialog(
    initialDate: LocalDate? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    yearRange: IntRange = DatePickerDefaults.YearRange,
    onDismiss: () -> Unit,
    onSelectDate: (LocalDate) -> Unit
) {
    val initialSelectedDateMillis = initialDate?.atStartOfDayIn(TimeZone.currentSystemDefault())?.toEpochMilliseconds()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis,
        yearRange = yearRange
    )

    DatePickerDialog(
        onDismissRequest = onDismiss, colors = datePickerDialogColors(),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp),
        confirmButton = {
            ConfirmDateButton(
                datePickerState = datePickerState,
                onSelectDate = onSelectDate,
                onDismiss = onDismiss
            )
        },
        dismissButton = {
            TudeeDismissButton(onDismiss = onDismiss)
        }
    ) {
        DatePicker(state = datePickerState, showModeToggle = false, colors = datePickerDialogColors())
    }
}

@Composable
private fun ConfirmDateButton(
    datePickerState: DatePickerState,
    onSelectDate: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    TextButton(
        onClick = {
            datePickerState.selectedDateMillis?.let { millis ->
                val localDate = Instant.fromEpochMilliseconds(millis)
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                onSelectDate(localDate)
            }
            onDismiss()
        }
    ) {
        Text(
            text = stringResource(R.string.ok),
            color = Theme.colors.primary
        )
    }
}

@Composable
private fun TudeeDismissButton(onDismiss: () -> Unit) {
    TextButton(
        onClick = onDismiss,
    ) {
        Text(
            text = stringResource(R.string.cancel),
            color = Theme.colors.primary
        )
    }
}

@Composable
private fun datePickerDialogColors(): DatePickerColors = DatePickerDefaults.colors(
    containerColor = Theme.colors.surfaceColors.surface,
    titleContentColor = Theme.colors.text.title,
    headlineContentColor = Theme.colors.text.title,
    weekdayContentColor = Theme.colors.text.title,
    subheadContentColor = Theme.colors.text.title,
    navigationContentColor = Theme.colors.text.title,
    yearContentColor = Theme.colors.text.title,
    currentYearContentColor = Theme.colors.text.title,
    dayContentColor = Theme.colors.text.title,
    selectedDayContainerColor = Theme.colors.primary,
    selectedYearContainerColor = Theme.colors.primary,
    todayDateBorderColor = Theme.colors.primary,
    todayContentColor = Theme.colors.primary
)

@Composable
@Preview
private fun TudeeDatePickerDialogPreview() {
    BasePreview {
        TudeeDatePickerDialog(
            initialDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            yearRange = 2000..2030,
            onDismiss = {},
            onSelectDate = {}
        )
    }
}