package com.luna.budgetapp.domain.usecase.expense

import com.luna.budgetapp.domain.repository.ExpenseRepository

class DeleteLatestExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke() {
        repository.deleteLatestExpense()
    }
}
