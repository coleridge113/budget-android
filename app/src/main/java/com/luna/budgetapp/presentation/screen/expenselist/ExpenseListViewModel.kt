package com.luna.budgetapp.presentation.screen.expenselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.usecase.UseCases
import com.luna.budgetapp.domain.model.DateFilter
import com.luna.budgetapp.presentation.model.ChartData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ExpenseListViewModel(
    private val useCases: UseCases
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val expensesPagingFlow: Flow<PagingData<Expense>> = 
        _uiState
            .map { it.selectedRange }
            .distinctUntilChanged()
            .flatMapLatest { filter ->
                val range = filter.resolve()

                useCases.getPagingExpensesByDateRange(range.start, range.end)
            }
            .cachedIn(viewModelScope)

    init {
        observeExpenses()
        computeChartData()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.DismissDialog -> dismissDialog()
            Event.ShowCalendarForm -> showCalendarForm()
            is Event.ShowDeleteConfirmationDialog -> showDeleteConfirmationDialog(event.expenseId)
            is Event.DeleteExpense -> deleteExpense(event.expenseId)
            is Event.SelectDateRange -> selectDateRange(event.selectedRange)
        }
    }

    private fun observeExpenses() {
        viewModelScope.launch {
            _uiState
                .map { it.selectedRange }
                .distinctUntilChanged()
                .flatMapLatest { filter ->
                    val range = filter.resolve()

                    useCases.getTotalAmountByDateRange(range.start, range.end)
                }
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isExpensesLoading = false,
                            error = error.localizedMessage
                        )
                    }
                }
                .collect { totalAmount ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isExpensesLoading = false,
                            error = null,
                            totalAmount = totalAmount
                        )
                    }
                }
        }
    }

    private fun showDeleteConfirmationDialog(expenseId: Long) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    dialogState = DialogState.ConfirmDeleteExpense(expenseId)
                )
            }
        }
    }

    private fun dismissDialog() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    dialogState = null
                )
            }
        }
    }

    private fun deleteExpense(expenseId: Long) {
        viewModelScope.launch {
            useCases.deleteExpense(expenseId)
            _uiState.update { currentState ->
                currentState.copy(
                    dialogState = null
                )
            }
        }
    }

    private fun computeChartData() {
        viewModelScope.launch {
            _uiState                         
                .map { it.selectedRange }
                .distinctUntilChanged()
                .flatMapLatest { filter ->
                    val range = filter.resolve()
                    
                    useCases.getCategoryTotalsByDateRange(range.start, range.end)
                }
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isExpensesLoading = false,
                            error = error.localizedMessage
                        )
                    }
                }
                .collect { categoryAmounts ->
                    val chartData = categoryAmounts.map { 
                        ChartData(
                            category = it.category,
                            value = it.total
                        )
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            isExpensesLoading = false,
                            error = null,
                            chartDataList = chartData
                        )
                    }
                }
        }
    }

    private fun showCalendarForm() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    dialogState = DialogState.CalendarForm
                )
            }
        }
    }

    private fun selectDateRange(selectedRange: DateFilter) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    selectedRange = selectedRange,
                    dialogState = null
                )
            }
        }
    }
}
