package com.luna.budgetapp.presentation.screen.expensepreset

import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.model.DateFilter
import java.time.LocalDateTime

data class UiState(
    val isExpensesLoading: Boolean = false,
    val isPresetsLoading: Boolean = false,
    val error: String? = null,
    val expensePresets: List<ExpensePreset> = emptyList(),
    val expenses: List<Expense> = emptyList(),
    val dialogState: DialogState? = null,
    val selectedRange: DateFilter = DateFilter.Daily,
    val totalAmount: Double = 0.0
)

sealed interface DialogState {
    data object CalendarForm : DialogState
    data class ExpenseForm(
        val selectedPreset: ExpensePreset? = null,
        val isSaving: Boolean = false
    ) : DialogState
    data class ConfirmDeleteExpensePreset(val expensePresetId: Long) : DialogState
    data class ConfirmDeleteExpense(val expenseId: Long?) : DialogState
}

sealed interface Event {
    data object GotoExpenseRoute : Event
    data object DismissDialog : Event
    data object ShowCalendarForm : Event
    data object ShowDeleteConfirmationDialog : Event
    data class SelectDateRange(val selectedRange: DateFilter) : Event
    data class ShowExpenseForm(val selectedPreset: ExpensePreset? = null) : Event
    data class ShowConfirmationDialog(val expensePresetId: Long) : Event
    data class ConfirmDialog(val category: String, val type: String, val amount: String) : Event
    data class AddExpense(val expensePreset: ExpensePreset, val customAmount: Double? = null) : Event
    data class AddCustomExpense(val selectedPreset: ExpensePreset) : Event
    data class DeleteExpense(val expenseId: Long?) : Event
    data class DeleteExpensePreset(val expensePresetId: Long) : Event
}

sealed interface Navigation {
    data object GotoExpenseRoute : Navigation
}
