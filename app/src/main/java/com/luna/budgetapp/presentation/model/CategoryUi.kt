package com.luna.budgetapp.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import com.luna.budgetapp.ui.icons.BillsIcon
import com.luna.budgetapp.ui.icons.CoffeeIcon
import com.luna.budgetapp.ui.icons.FoodIcon
import com.luna.budgetapp.ui.icons.HeartIcon
import com.luna.budgetapp.ui.icons.HouseIcon
import com.luna.budgetapp.ui.icons.MotorcycleIcon
import com.luna.budgetapp.ui.theme.BeverageChartColor
import com.luna.budgetapp.ui.theme.BillsChartColor
import com.luna.budgetapp.ui.theme.CommuteChartColor
import com.luna.budgetapp.ui.theme.DateChartColor
import com.luna.budgetapp.ui.theme.FoodChartColor
import com.luna.budgetapp.ui.theme.HouseChartColor

enum class CategoryOptions(
    val displayName: String,
    val icon: ImageVector,
    val chartColor: Color
) {
    FOOD("Food", FoodIcon, FoodChartColor),
    BEVERAGE("Beverage", CoffeeIcon, BeverageChartColor),
    DATE("Date", HeartIcon, DateChartColor),
    HOUSE("House", HouseIcon, HouseChartColor),
    COMMUTE("Commute", MotorcycleIcon, CommuteChartColor),
    BILLS("Bills", BillsIcon, BillsChartColor)
}
