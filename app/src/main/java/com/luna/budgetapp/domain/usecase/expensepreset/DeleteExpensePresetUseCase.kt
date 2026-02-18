package com.luna.budgetapp.domain.usecase.expensepreset

import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import android.util.Log

class DeleteExpensePresetUseCase(
    private val repository: ExpensePresetRepository
) {
    suspend operator fun invoke(expensePresetId: Long) {
        try {
            repository.deleteExpensePreset(expensePresetId)
        } catch(e: Exception) {
            Log.e("UseCase", "Error deleting preset: ${e.localizedMessage}")
        }
    }
}
