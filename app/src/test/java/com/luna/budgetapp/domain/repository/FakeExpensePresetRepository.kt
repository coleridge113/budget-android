package com.luna.budgetapp.domain.repository

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.domain.model.ExpensePreset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeExpensePresetRepository : ExpensePresetRepository {
    private val presets = mutableListOf<ExpensePreset>()
    private val flow = MutableStateFlow<Resource<List<ExpensePreset>>>(Resource.Success(emptyList()))

    override fun getAllExpensePresets(): Flow<Resource<List<ExpensePreset>>> = flow

    override suspend fun addExpensePresets(expensePresets: List<ExpensePreset>) {
        TODO("Not yet implemented")
    }

    override suspend fun addExpensePreset(expensePreset: ExpensePreset) {
        presets.add(expensePreset)
        flow.value = Resource.Success(presets.toList())
    }
}
