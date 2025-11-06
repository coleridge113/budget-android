package com.luna.budgetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luna.budgetapp.ui.screen.addexpense.AddExpenseViewModel
import com.luna.budgetapp.ui.screen.addexpense.AddExpensesScreen
import com.luna.budgetapp.ui.theme.BudgetAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "add_expense_screen"
                ) {
                    composable("add_expense_screen") {
                        AddExpensesScreen(navController)
                    }
                }
            }
        }
    }
}