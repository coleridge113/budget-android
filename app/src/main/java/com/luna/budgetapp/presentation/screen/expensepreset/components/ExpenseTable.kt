package com.luna.budgetapp.presentation.screen.expensepreset.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.presentation.model.CategoryOptions
import com.luna.budgetapp.ui.icons.CoffeeIcon
import com.luna.budgetapp.ui.icons.FoodIcon

@Composable
fun ExpenseTable(
    expensePresets: List<ExpensePreset>,
    modifier: Modifier = Modifier,
    onClickIcon: (ExpensePreset) -> Unit,
    onClickItem: (ExpensePreset) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expensePresets) { expensePreset ->
                ExpenseItem(
                    item = expensePreset,
                    icon = iconSelector(expensePreset.category),
                    onClickIcon = { onClickIcon(expensePreset) },
                    onClickItem = { onClickItem(expensePreset) }
                )            
            }
        }
    } 
}

@Composable
fun ExpenseItem(
    item: ExpensePreset,
    icon: ImageVector,
    onClickIcon: (ExpensePreset) -> Unit,
    onClickItem: (ExpensePreset) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f)
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                .clickable { onClickIcon(item) },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Spacer(Modifier.width(4.dp))
        Box(
            modifier = Modifier.weight(3f)
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .clickable { onClickItem(item) },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "${item.type} - P${item.amount}")
        }
    }
}

@Preview(showBackground = true)
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
        onClickIcon = {},
        onClickItem = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ExpenseTablePreview() {
    val expensePresets = listOf(
        ExpensePreset(
            id = 1L,
            amount = 4.50,
            category = "Food & Drink",
            type = "Coffee"
        ),
        ExpensePreset(
            id = 2L,
            amount = 12.00,
            category = "Food & Drink",
            type = "Lunch"
        ),
        ExpensePreset(
            id = 3L,
            amount = 65.00,
            category = "Commute",
            type = "Angkas"
        ),
    )
    ExpenseTable(
        expensePresets = expensePresets,
        onClickItem = {},
        onClickIcon = {}
    )
}

private fun iconSelector(category: String): ImageVector {
    return CategoryOptions.entries
        .firstOrNull { it.displayName == category }
        ?.icon
        ?: FoodIcon
}
