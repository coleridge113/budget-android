package com.luna.budgetapp.presentation.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luna.budgetapp.presentation.model.CategoryOptions
import com.luna.budgetapp.ui.theme.MaterialBlue

@Composable
fun CategoryFilter() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(CategoryOptions.entries) { item ->
            var checked by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
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
    CategoryFilter()
}
