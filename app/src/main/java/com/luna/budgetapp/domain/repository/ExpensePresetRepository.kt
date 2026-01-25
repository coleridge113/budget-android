package com.luna.budgetapp.domain.repository

import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.common.Resource
import kotlinx.coroutines.flow.Flow

interface ExpensePresetRepository {

    fun getAllExpensePresets(): Flow<Resource<List<ExpensePreset>>>

    fun addExpensePresets(expensePresets: List<ExpensePreset)
}
