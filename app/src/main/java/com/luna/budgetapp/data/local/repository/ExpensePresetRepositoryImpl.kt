package com.luna.budgetapp.data.local.repository

import com.luna.budgetapp.data.local.dao.ExpensePresetDao
import com.luna.budgetapp.data.mapper.toEntity
import com.luna.budgetapp.data.mapper.toModel
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.network.ExpenseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class ExpensePresetRepositoryImpl(
    private val dao: ExpensePresetDao,
    private val api: ExpenseService
): ExpensePresetRepository {
    
    override fun getAllExpensePresets(): Flow<List<ExpensePreset>> =
        dao.getAllExpensePresets()
            .map { local ->
                local.map { it.toModel() }
            }
            .onStart {
                // try {
                //     val remote = api.getAllExpensePresets()
                //     dao.addExpensePresets(remote.map { it.toEntity() })
                // } catch (_: Exception) {}
            }
            .flowOn(Dispatchers.IO)

    override suspend fun addExpensePresets(expensePresets: List<ExpensePreset>) {
        dao.addExpensePresets(expensePresets.map { it.toEntity() })
    }

    override suspend fun addExpensePreset(expensePreset: ExpensePreset) {
        dao.addExpensePreset(expensePreset.toEntity())
    }

    override suspend fun deleteExpensePreset(expensePresetId: Long) {
        dao.deleteExpensePreset(expensePresetId)
    }
}
