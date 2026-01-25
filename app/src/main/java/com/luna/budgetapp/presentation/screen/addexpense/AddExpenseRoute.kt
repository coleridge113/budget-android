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
import com.luna.budgetapp.domain.model.Expense
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import com.luna.budgetapp.presentation.screen.addexpense.components.FontAwesomeCoffee

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

}

@Composable
fun TableRow(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Box(
            modifier = Modifier.weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun TableRowPreview() {
    TableRow(
        icon = FontAwesomeCoffee,
        text = "Description here"
    )
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
        type = "Commute"
    )

    PresetExpenseButton(expense)
}
