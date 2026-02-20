package com.luna.budgetapp.presentation.screen.expensepreset.components

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Long?, Long?) -> Unit
) {
    val state = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        state.selectedStartDateMillis,
                        state.selectedEndDateMillis
                    )
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            title = {},
            headline = {},
            showModeToggle = false,
            state = state
        )
    }
}

@Preview
@Composable
fun DateRangePickerDialogPreview() {
    DateRangePickerDialog(
        onDismiss = {},
        onConfirm = { _, _ -> }
    )
}
