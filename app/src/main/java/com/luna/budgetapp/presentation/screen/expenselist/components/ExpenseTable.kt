package com.luna.budgetapp.presentation.screen.expenselist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.presentation.screen.utils.getIconForCategory

@Composable
fun ExpenseTable(
    modifier: Modifier = Modifier,
    expenses: List<Expense>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expenses) { expense ->
            ExpenseItem(
                item = expense,
                icon = getIconForCategory(expense.category),
                onClickItem = {}
            )
        }
    }
}

@Composable
fun ExpenseItem(
    item: Expense,
    icon: ImageVector,
    onClickItem: (Expense) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Text(
            text = item.type,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "₱",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = "%,.2f".format(item.amount),
                modifier = Modifier
                    .widthIn(min = 48.dp)
                    .wrapContentWidth(Alignment.End)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExpenseTablePreview() {
    val expenses = listOf(
        Expense(
            id = 1L,
            amount = 4.50,
            category = "Beverage",
            type = "Coffee"
        ),
        Expense(
            id = 2L,
            amount = 12.00,
            category = "Food",
            type = "Lunch"
        ),
        Expense(
            id = 3L,
            amount = 65.00,
            category = "Commute",
            type = "Angkas"
        ),
        Expense(
            id = 3L,
            amount = 999.00,
            category = "Food",
            type = "Food"
        ),
        Expense(
            id = 3L,
            amount = 1999.00,
            category = "Food",
            type = "Food"
        ),
    )
    ExpenseTable(expenses = expenses)
}
