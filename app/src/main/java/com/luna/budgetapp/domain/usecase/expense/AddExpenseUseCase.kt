package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.toCategory
import com.luna.budgetapp.domain.repository.ExpenseRepository
import java.time.LocalDateTime

class AddExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(
        category: String,
        type: String,
        amount: Long,
        date: LocalDateTime = LocalDateTime.now()
    ) {
        val expense = Expense(
            category = category.toCategory(),
            type = type,
            amount = amount,
            date = date
        )
        repository.addExpense(expense)
    }
}
