package com.luna.budgetapp.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Money
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import com.luna.budgetapp.ui.icons.BillsIcon
import com.luna.budgetapp.ui.icons.CoffeeIcon
import com.luna.budgetapp.ui.icons.FoodIcon
import com.luna.budgetapp.ui.icons.GroceryIcon
import com.luna.budgetapp.ui.icons.HeartIcon
import com.luna.budgetapp.ui.icons.HouseIcon
import com.luna.budgetapp.ui.icons.MotorcycleIcon
import com.luna.budgetapp.ui.theme.BeverageChartColor
import com.luna.budgetapp.ui.theme.BillsChartColor
import com.luna.budgetapp.ui.theme.CommuteChartColor
import com.luna.budgetapp.ui.theme.DateChartColor
import com.luna.budgetapp.ui.theme.FoodChartColor
import com.luna.budgetapp.ui.theme.GroceryChartColor
import com.luna.budgetapp.ui.theme.HouseChartColor
import com.luna.budgetapp.ui.theme.OthersChartColor
import com.luna.budgetapp.ui.theme.FitnessChartColor

enum class CategoryOptions(
    val displayName: String,
    val icon: ImageVector,
    val chartColor: Color
) {
    FOOD("Food", FoodIcon, FoodChartColor),
    DATE("Date", HeartIcon, DateChartColor),
    BEVERAGE("Beverage", CoffeeIcon, BeverageChartColor),
    HOUSE("House", HouseIcon, HouseChartColor),
    COMMUTE("Commute", MotorcycleIcon, CommuteChartColor),
    BILLS("Bills", BillsIcon, BillsChartColor),
    GROCERY("Grocery", GroceryIcon, GroceryChartColor),
    FITNESS("Fitness", Icons.Default.FitnessCenter, FitnessChartColor),
    OTHERS("Others", Icons.Default.Money, OthersChartColor)
}
