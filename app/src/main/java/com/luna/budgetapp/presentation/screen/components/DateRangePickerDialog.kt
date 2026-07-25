package com.luna.budgetapp.presentation.screen.components

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luna.budgetapp.R

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
                Text(stringResource(R.string.btn_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.btn_cancel))
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
