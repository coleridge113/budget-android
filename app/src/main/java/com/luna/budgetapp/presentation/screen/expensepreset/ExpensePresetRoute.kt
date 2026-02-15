package com.luna.budgetapp.presentation.screen.expensepreset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.luna.budgetapp.presentation.screen.expensepreset.components.ExpensePresetDialog
import com.luna.budgetapp.presentation.screen.expensepreset.components.ExpenseTable
import com.luna.budgetapp.ui.icons.CirclePlusIcon
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpensePresetRoute(
    navController: NavController,
    viewModel: ExpensePresetViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box (modifier = Modifier.fillMaxSize()){
        Scaffold { innerPadding ->
            MainContent(
                uiState = state,
                modifier = Modifier.padding(innerPadding)
                    .padding(16.dp),
                onEvent = viewModel::onEvent,
            )
        }

        FloatingActionButton(
            modifier = Modifier.align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            onClick = { viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset) },
            shape = CircleShape
        ) {
            Icon(
                imageVector = CirclePlusIcon,
                contentDescription = null
            )
        }
    }
}

@Composable
fun MainContent(
    uiState: ViewModelStateEvents.UiState,
    modifier: Modifier = Modifier,
    onEvent: (ViewModelStateEvents.Event) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .clickable {},
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (uiState.totalAmount == 0.0) "-" else "P${uiState.totalAmount}",
                style = MaterialTheme.typography.displayMedium
            ) 
        }
        ExpenseTable(
            expensePresets = uiState.expensePresets,
            onClickItem = { onEvent(ViewModelStateEvents.Event.AddExpense(it))},
            onClickAdd = { onEvent(ViewModelStateEvents.Event.AddExpensePreset) },
            modifier = Modifier.weight(3f)
        )

        if (uiState.isDialogVisible) {
            ExpensePresetDialog(
                onDismissRequest = { onEvent(ViewModelStateEvents.Event.DismissDialog) },
                onConfirm = { category, type, amount ->
                    onEvent(ViewModelStateEvents.Event.ConfirmDialog(category, type, amount))
                },
                isSaving = uiState.isSaving
            )
        }
    }
}
