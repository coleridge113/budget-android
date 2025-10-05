package com.luna.budgetapp.data.local.repository

import com.luna.budgetapp.data.local.AppDatabase
import com.luna.budgetapp.data.local.entity.ExpenseCache

class RepositoryImpl(
    private val db: AppDatabase
) : Repository {
    override suspend fun getAllExpenses(): List<ExpenseCache> {
        TODO("Not yet implemented")
    }

    override suspend fun getExpensesByCategory(category: String): List<ExpenseCache> {
        TODO("Not yet implemented")
    }

    override suspend fun getExpensesByType(type: String): List<ExpenseCache> {
        TODO("Not yet implemented")
    }

    override suspend fun addExpense(expenseCache: ExpenseCache) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExpense(
        expenseCache: ExpenseCache,
        id: Long
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(id: Long) {
        TODO("Not yet implemented")
    }

}