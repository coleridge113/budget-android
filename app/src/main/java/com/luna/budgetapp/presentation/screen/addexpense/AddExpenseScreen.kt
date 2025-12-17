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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luna.budgetapp.domain.model.Expense
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import android.util.Log

@Composable
fun AddExpensesScreen(
    navController: NavController,
    viewModel: AddExpenseViewModel = koinViewModel()
) {
    Column(modifier = Modifier) {
        val state by viewModel.state.collectAsState(initial = AddExpenseState.GetExpenses(emptyList()))
        LaunchedEffect(Unit) { viewModel.getAllExpenses() }

        when(state) {
            is AddExpenseState.GetExpenses -> {
                val expenseList = (state as AddExpenseState.GetExpenses).expenses
                Log.d("ExpenseScreen", "expense list: ${expenseList}")
                if (expenseList.isEmpty()) {
                    Text("No expenses found", modifier = Modifier.padding(16.dp))
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                    ) {
                        items(expenseList) { expense ->
                            PresetExpenseButton(
                                expense = expense,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = { viewModel.getAllExpenses() }
        ) {
            Text(text = "Get all expenses")
        }
    }
}

@Composable
fun PresetExpenseButton(
    expense: Expense,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Black)
    ) {
        Text(
            text = expense.amount.toString(),
            color = Color.White,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = modifier
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PresetExpenseButtonPreview() {
    val expense = Expense(
        id = 1,
        name = "AM Commute",
        amount = 16.00,
        category = "Work",
        type = "Commute",
        date = LocalDate.now().toString()
    )
    PresetExpenseButton(expense)
}
