package com.luna.budgetapp.presentation.screen.addexpense

import com.luna.budgetapp.domain.model.Expense

sealed class AddExpenseState {
    data class GetExpenses(val expenses: List<Expense>) : AddExpenseState()
}