package com.luna.budgetapp.presentation.screen.expenselist

import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.DateFilter
import com.luna.budgetapp.presentation.model.ChartData

data class UiState(
    val isExpensesLoading: Boolean = false,
    val error: String? = null,
    val expenses: List<Expense> = emptyList(),
    val dialogState: DialogState? = null,
    val selectedRange: DateFilter = DateFilter.Daily,
    val chartDataList: List<ChartData> = emptyList(),
    val filteredCategories: List<String>? = null,
    val totalAmount: Double = 0.0
) 

sealed interface DialogState {
    data object CalendarForm : DialogState
    data class ConfirmDeleteExpense(val expenseId: Long) : DialogState
}

sealed interface Event {
    data object ShowCalendarForm : Event
    data object DismissDialog : Event
    data class ShowDeleteConfirmationDialog(val expenseId: Long) : Event
    data class DeleteExpense(val expenseId: Long) : Event
    data class SelectDateRange(val selectedRange: DateFilter) : Event
}

sealed interface Navigation {}
