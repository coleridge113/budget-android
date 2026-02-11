package com.luna.budgetapp.presentation.screen.expensepreset.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.ui.icons.BillsIcon
import com.luna.budgetapp.ui.icons.CoffeeIcon
import com.luna.budgetapp.ui.icons.FoodIcon
import com.luna.budgetapp.ui.icons.HeartIcon
import com.luna.budgetapp.ui.icons.HouseIcon
import com.luna.budgetapp.ui.icons.MotorcycleIcon

@Composable
fun ExpenseTable(
    expensePresets: List<ExpensePreset>,
    modifier: Modifier = Modifier,
    onClickItem: (ExpensePreset) -> Unit,
    onClickAdd: () -> Unit
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
                    onClick = { onClickItem(expensePreset) }
                )            
            }
        }
    } 
}

@Composable
fun ExpenseItem(
    item: ExpensePreset,
    icon: ImageVector,
    onClick: (ExpensePreset) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick(item) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f)
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
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
            ,
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
        onClick = {}
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
        onClickAdd = {}
    )
}

private fun iconSelector(category: String): ImageVector {
    return CategoryOptions.entries
        .firstOrNull { it.displayName == category }
        ?.icon
        ?: FoodIcon
}

enum class CategoryOptions(val displayName: String, val icon: ImageVector) {
    FOOD("Food", FoodIcon),
    BEVERAGE("Beverage", CoffeeIcon),
    DATE("Date", HeartIcon),
    HOUSE("House", HouseIcon),
    COMMUTE("Commute", MotorcycleIcon),
    BILLS("Bills", BillsIcon)
}
