package com.luna.budgetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.luna.budgetapp.ui.screen.addexpense.AddExpenseViewModel
import com.luna.budgetapp.ui.screen.addexpense.AddExpensesScreen
import com.luna.budgetapp.ui.theme.BudgetAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val addExpenseViewModel: AddExpenseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetAppTheme {
                AddExpensesScreen(addExpenseViewModel)
            }
        }
    }
}