package com.luna.budgetapp.presentation.screen.expensepreset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luna.budgetapp.presentation.screen.expensepreset.components.ExpensePresetDialog
import org.koin.compose.viewmodel.koinViewModel
import com.luna.budgetapp.presentation.screen.expensepreset.components.ExpenseTable

@Composable
fun ExpensePresetRoute(
    navController: NavController,
    viewModel: ExpensePresetViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        MainContent(
            uiState = state,
            modifier = Modifier.padding(innerPadding),
            onEvent = viewModel::onEvent,
        )
    }
}

@Composable
fun MainContent(
    uiState: ViewModelStateEvents.UiState,
    modifier: Modifier = Modifier,
    onEvent: (ViewModelStateEvents.Event) -> Unit,
) {
    Box(modifier = modifier) {
        ExpenseTable(
            expensePresets = uiState.expensePresets,
            onClickItem = {},
            onClickAdd = { onEvent(ViewModelStateEvents.Event.AddExpensePreset) }
        )

        if (uiState.isDialogVisible) {
            ExpensePresetDialog(
                onDismissRequest = { onEvent(ViewModelStateEvents.Event.DismissDialog) },
                onConfirm = { category, amount ->
                    onEvent(ViewModelStateEvents.Event.ConfirmDialog(category, amount))
                }
            )
        }
    }
}
