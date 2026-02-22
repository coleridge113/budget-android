package com.luna.budgetapp.presentation.screen.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.luna.budgetapp.presentation.model.CategoryOptions
import com.luna.budgetapp.ui.icons.FoodIcon


fun iconSelector(category: String): ImageVector {
    return CategoryOptions.entries
        .firstOrNull { it.displayName == category }
        ?.icon
        ?: FoodIcon
}
