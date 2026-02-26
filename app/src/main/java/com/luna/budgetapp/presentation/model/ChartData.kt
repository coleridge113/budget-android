package com.luna.budgetapp.presentation.model

import com.luna.budgetapp.ui.theme.OthersChartColor

data class ChartData(
    val category: String,
    val subtotal: Double,
) {
    val color = CategoryOptions.entries
        .find { it.displayName == category }
        ?.chartColor
        ?: OthersChartColor
}
