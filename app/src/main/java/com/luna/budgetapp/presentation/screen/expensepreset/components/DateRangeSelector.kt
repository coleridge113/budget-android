package com.luna.budgetapp.presentation.screen.expensepreset.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luna.budgetapp.presentation.model.DateFilter

@Composable
fun DateRangeSelector(
    selected: DateFilter,
    onSelectedChange: (DateFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DateFilter.entries.forEachIndexed { index, range ->
                DateRangeItem(
                    range = range,
                    selected = selected,
                    onClick = { onSelectedChange(range) }
                )

                if (index < DateFilter.entries.lastIndex) {
                    VerticalDivider(
                        modifier = Modifier.height(24.dp),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun DateRangeItem(
    range: DateFilter,
    selected: DateFilter,
    onClick: () -> Unit
) {
    val isSelected = range == selected

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primaryContainer
        else
            Color.Transparent,
        label = "dateRangeBg"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.onPrimaryContainer
        else
            MaterialTheme.colorScheme.onSurface,
        label = "dateRangeContent"
    )

    TextButton(
        modifier = Modifier.padding(4.dp),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Text(range.value)
    }
}

@Preview
@Composable
fun DateRangeSelectorPreview() {
    DateRangeSelector(
        selected = DateFilter.entries.first(),
        onSelectedChange = {}
    )
}
