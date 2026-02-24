package com.luna.budgetapp.presentation.screen.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.luna.budgetapp.presentation.model.CategoryOptions
import com.luna.budgetapp.ui.icons.FoodIcon
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

fun getIconForCategory(category: String): ImageVector {
    return CategoryOptions.entries
        .firstOrNull { it.displayName == category }
        ?.icon
        ?: FoodIcon
}

fun Double.formatToCurrency(): String {
    return "%,.2f".format(this)
}

fun Double.formatToPercentage(): String {
    val percent = (this * 100).roundToInt()
    return "${percent.coerceAtLeast(0)}%"
}

fun LocalDateTime.formatToDisplay(): String {
    val formatter = DateTimeFormatter.ofPattern(
        "dd MMMM yyyy",
        Locale.getDefault()
    )
    return this.format(formatter)
}
