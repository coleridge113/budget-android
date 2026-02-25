package com.luna.budgetapp.presentation.screen.expenselist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.luna.budgetapp.domain.model.DateFilter
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.presentation.screen.components.ConfirmationDialog
import com.luna.budgetapp.presentation.screen.components.DateRangePickerDialog
import com.luna.budgetapp.presentation.screen.components.DateRangeSelectorDropdown
import com.luna.budgetapp.presentation.screen.expenselist.components.ExpenseChart
import com.luna.budgetapp.presentation.screen.expenselist.components.ExpenseTable
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListRoute(
    navController: NavController,
    viewModel: ExpenseListViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    DateRangeSelectorDropdown(
                        selected = uiState.selectedRange,
                        onSelectedChange = {
                            when (it) {
                                DateFilter.Daily,
                                DateFilter.Weekly,
                                DateFilter.Monthly -> viewModel.onEvent(Event.SelectDateRange(it))
                                else -> viewModel.onEvent(Event.ShowCalendarForm)
                            }
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        MainContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent,
            expensesPagingFlow = viewModel.expensesPagingFlow
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    uiState: UiState,
    onEvent: (Event) -> Unit,
    expensesPagingFlow: Flow<PagingData<Expense>>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        ExpenseChart(
            chartDataList = uiState.chartDataList,
            totalAmount = uiState.totalAmount
        )
        Spacer(modifier = Modifier.height(48.dp))
        if (uiState.totalAmount <= 0) {
            Text(
                text = "No expenses for the filtered range!"
            )
        } else {
            ExpenseTable(
                modifier = Modifier,
                expenses = expensesPagingFlow.collectAsLazyPagingItems(),
                onClick = {},
                onLongClick = { onEvent(Event.ShowDeleteConfirmationDialog(it.id!!)) }
            )
        }

        when (val dialog = uiState.dialogState) {
            DialogState.CalendarForm ->
                DateRangePickerDialog(
                    onDismiss = { onEvent(Event.DismissDialog) },
                    onConfirm = { start, end ->
                        when {
                            start == null -> onEvent(Event.DismissDialog)
                            else -> onEvent(Event.SelectDateRange(DateFilter.Custom(start, end)))
                        }
                    }
                )
            is DialogState.ConfirmDeleteExpense -> {
                ConfirmationDialog(
                    message = "Delete this expense?",
                    confirmText = "Delete",
                    isDestructive = true,
                    onDismiss = { onEvent(Event.DismissDialog) },
                    onConfirm = { onEvent(Event.DeleteExpense(dialog.expenseId)) }
                )

            }
            else -> {}
        }
    }
}
