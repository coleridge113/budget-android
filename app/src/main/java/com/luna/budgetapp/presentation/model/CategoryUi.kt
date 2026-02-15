package com.luna.budgetapp.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.luna.budgetapp.ui.icons.BillsIcon
import com.luna.budgetapp.ui.icons.CoffeeIcon
import com.luna.budgetapp.ui.icons.FoodIcon
import com.luna.budgetapp.ui.icons.HeartIcon
import com.luna.budgetapp.ui.icons.HouseIcon
import com.luna.budgetapp.ui.icons.MotorcycleIcon

enum class CategoryOptions(val displayName: String, val icon: ImageVector) {
    FOOD("Food", FoodIcon),
    BEVERAGE("Beverage", CoffeeIcon),
    DATE("Date", HeartIcon),
    HOUSE("House", HouseIcon),
    COMMUTE("Commute", MotorcycleIcon),
    BILLS("Bills", BillsIcon)
}
