package com.luna.budgetapp.presentation.screen.expenselist

import androidx.paging.PagingData
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.DateFilter

data class UiState(
    val isExpensesLoading: Boolean = false,
    val error: String? = null,
    val expenses: List<Expense> = emptyList(),
    val expensesPaging: PagingData<Expense> = PagingData.empty(),
    val dialogState: DialogState? = null,
    val selectedRange: DateFilter = DateFilter.Daily
)

sealed interface DialogState {
    data object CalendarForm : DialogState
    data class ConfirmDeleteExpense(val expenseId: Long) : DialogState
}

sealed interface Event {
    data object DismissDialog : Event
    data class ShowDeleteConfirmationDialog(val expenseId: Long) : Event
    data class DeleteExpense(val expenseId: Long) : Event
}

sealed interface Navigation {}
