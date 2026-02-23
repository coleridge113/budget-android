package com.luna.budgetapp.presentation.model

import androidx.compose.ui.graphics.Color

data class ChartData(
    val category: String,
    val value: Double,
) {
    val color = CategoryOptions.entries
        .find { it.displayName == category }
        ?.chartColor
        ?: Color.Gray
}
