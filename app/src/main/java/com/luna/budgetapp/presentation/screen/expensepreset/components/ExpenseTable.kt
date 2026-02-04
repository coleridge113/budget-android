package com.luna.budgetapp.presentation.screen.expensepreset.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.ui.icons.CoffeeIcon
import com.luna.budgetapp.ui.icons.CirclePlusIcon

@Composable
fun ExpenseTable(
    expensePresets: List<ExpensePreset>,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
    onClickAdd: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(expensePresets) { item ->
                ExpenseItem(
                    item = item,
                    icon = CoffeeIcon,
                    text = "More contents",
                    onClick = onClickItem
                )            
            }
        }
        AddItemPrompt(onClick = onClickAdd)
    } 
}

@Composable
fun ExpenseItem(
    item: ExpensePreset,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
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

@Composable
fun AddItemPrompt(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = modifier.clickable { onClick() }) {
        Icon(
            imageVector = CirclePlusIcon,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun ExpenseItemPreview() {
    val item = ExpensePreset(
            id = 1L,
            amount = 4.50,
            category = "Coffee",
            type = "Food & Drink"
        )
    ExpenseItem(
        item = item,
        icon = CoffeeIcon,
        text = "Description here",
        onClick = {}
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
        expensePresets = expensePresets,
        onClickItem = {},
        onClickAdd = {}
    )
}
