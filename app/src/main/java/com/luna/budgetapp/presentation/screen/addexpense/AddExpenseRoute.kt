package com.luna.budgetapp.presentation.screen.addexpense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luna.budgetapp.domain.model.ExpensePreset
import org.koin.compose.viewmodel.koinViewModel
import com.luna.budgetapp.presentation.screen.addexpense.components.ExpenseTable

@Composable
fun AddExpensesRoute(
    navController: NavController,
    viewModel: AddExpenseViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        MainContent(
            uiState = state,
            modifier = Modifier.padding(innerPadding),
            onClickItem = {}
        )
    }
}

@Composable
fun MainContent(
    uiState: ViewModelStateEvents.UiState,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit
) {
    val expensePresets = listOf(
        ExpensePreset(
            id = 1L,
            amount = 4.50,
            category = "Coffee",
            type = "Food & Drink"
        ),
        ExpensePreset(
            id = 2L,
            amount = 12.00,
            category = "Lunch",
            type = "Food & Drink"
        ),
        ExpensePreset(
            id = 3L,
            amount = 65.00,
            category = "Gas",
            type = "Transport"
        ),
        ExpensePreset(
            id = 4L,
            amount = 15.99,
            category = "Streaming",
            type = "Entertainment"
        ),
        ExpensePreset(
            id = 5L,
            amount = 120.00,
            category = "Groceries",
            type = "Housekeeping"
        )
    )
    Box(modifier = modifier) {
        ExpenseTable(
            expensePresets = expensePresets,
            onClickItem = {}
        )

    }
}
