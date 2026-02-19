package com.luna.budgetapp.presentation.screen.expensepreset

import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.presentation.model.DateFilter

data class UiState(
    val isExpensesLoading: Boolean = false,
    val isPresetsLoading: Boolean = false,
    val error: String? = null,
    val expensePresets: List<ExpensePreset> = emptyList(),
    val expenses: List<Expense> = emptyList(),
    val dialogState: DialogState? = null
) {
    val totalAmount: Double
        get() = expenses.sumOf { it.amount }
}

sealed class DialogState {
    data class ExpenseForm(
        val selectedPreset: ExpensePreset? = null,
        val isSaving: Boolean = false
    ) : DialogState()
    data class ConfirmDeleteExpensePreset(val expensePresetId: Long) : DialogState()
    data class ConfirmDeleteExpense(val expenseId: Long) : DialogState()
}

sealed interface Event {
    data object DismissDialog : Event
    data object CycleDateFilter : Event
    data class ShowExpenseForm(val selectedPreset: ExpensePreset? = null) : Event
    data class ShowConfirmationDialog(val expensePresetId: Long) : Event
    data class ConfirmDialog(val category: String, val type: String, val amount: String) : Event
    data class AddExpense(val expensePreset: ExpensePreset) : Event
    data class AddCustomExpense(val selectedPreset: ExpensePreset) : Event
    data class DeleteExpense(val expenseId: Long) : Event
    data class DeleteExpensePreset(val expensePresetId: Long) : Event
}

sealed class Navigation {
}
