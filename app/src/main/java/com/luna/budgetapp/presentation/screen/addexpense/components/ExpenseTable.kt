package com.luna.budgetapp.presentation.screen.addexpense.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.luna.budgetapp.domain.model.ExpensePreset

@Composable
fun ExpenseTable(
    expensePresets: List<ExpensePreset>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(expensePresets.size) { item ->
            TableRow(
                icon = FontAwesomeCoffee,
                text = "More contents"
            )            
        }
    }
}

@Composable
fun TableRow(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Box(
            modifier = Modifier.weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun TableRowPreview() {
    TableRow(
        icon = FontAwesomeCoffee,
        text = "Description here"
    )
}

@Preview
@Composable
fun ExpenseTablePreview() {
    val expensePresets = listOf(
        ExpensePreset(
            id = 1L,
            amount = 4.50,
            category = "Coffee",
            type = "Food & Drink"
        ),
        ExpensePreset(
            id = 2L,
            amount = 12.00,
            category = "Lunch",
            type = "Food & Drink"
        ),
        ExpensePreset(
            id = 3L,
            amount = 65.00,
            category = "Gas",
            type = "Transport"
        ),
        ExpensePreset(
            id = 4L,
            amount = 15.99,
            category = "Streaming",
            type = "Entertainment"
        ),
        ExpensePreset(
            id = 5L,
            amount = 120.00,
            category = "Groceries",
            type = "Housekeeping"
        )
    )
    ExpenseTable(
        expensePresets = expensePresets
    )
}
