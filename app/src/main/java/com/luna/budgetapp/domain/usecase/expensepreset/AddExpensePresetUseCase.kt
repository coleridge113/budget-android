package com.luna.budgetapp.domain.usecase.expensepreset

import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.domain.model.ExpensePreset

class AddExpensePresetUseCase(
    private val repository: ExpensePresetRepository
) {
    suspend operator fun invoke(expensePreset: ExpensePreset) {
        repository.addExpensePreset(expensePreset)
    }
}
