package com.luna.budgetapp.presentation.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luna.budgetapp.presentation.model.CategoryOptions
import com.luna.budgetapp.ui.theme.MaterialBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterDialog(
    modifier: Modifier = Modifier,
    selectedCategoryMap: Map<String, Boolean>,
    onDismiss: () -> Unit,
    onConfirm: (Map<String, Boolean>) -> Unit,
    dismissText: String = "Cancel",
    confirmText: String = "Apply"
) {
    var tempMap by remember(selectedCategoryMap) {
        mutableStateOf(selectedCategoryMap)
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                CategoryFilter(
                    selectedCategoryMap = tempMap,
                    onCheckedChange = { category, isChecked ->
                        tempMap = tempMap + (category to isChecked)
                    }
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(dismissText)
                    }
                    TextButton(
                        onClick = { onConfirm(tempMap) }
                    ) {
                        Text(
                            text = confirmText,
                            color = MaterialBlue
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun CategoryFilter(
    selectedCategoryMap: Map<String, Boolean>,
    onCheckedChange: (String, Boolean) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center
    ) {
        items(CategoryOptions.entries) { item ->

            val isChecked =
                selectedCategoryMap[item.displayName]!!

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    onCheckedChange(item.displayName, !isChecked)                    
                }
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        onCheckedChange(item.displayName, checked)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialBlue
                    )
                )

                Text(text = item.displayName)
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun CategoryFilterPreview() {
    val selectedCategoryMap = mapOf(
        "Food" to true,
        "Beverage" to true,
        "Date" to false,
        "House" to false,
        "Commute" to true,
        "Bills" to false,
        "Grocery" to false,
        "Others" to true
    )

    Surface(
        color = Color.White
    ) {
        CategoryFilterDialog(
            selectedCategoryMap = selectedCategoryMap,
            onDismiss = {},
            onConfirm = { _ -> }
        )
    }
}
