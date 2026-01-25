package com.luna.budgetapp.presentation.screen.addexpense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luna.budgetapp.domain.model.ExpensePreset
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import com.luna.budgetapp.presentation.screen.addexpense.components.FontAwesomeCoffee
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
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun MainContent(
    uiState: ViewModelStateEvents.UiState,
    modifier: Modifier = Modifier
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
            expensePresets = expensePresets
        )

    }
}
