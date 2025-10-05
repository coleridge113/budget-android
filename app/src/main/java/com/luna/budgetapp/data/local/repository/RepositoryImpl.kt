package com.luna.budgetapp.data.local.repository

import com.luna.budgetapp.data.local.dao.ExpenseDao
import com.luna.budgetapp.data.local.entity.ExpenseCache

class RepositoryImpl(
    private val localSource: ExpenseDao,
) : Repository {

    override suspend fun getAllExpenses(): List<ExpenseCache> {
        return localSource.getAllExpenses()
    }

    override suspend fun getExpensesByCategory(category: String): List<ExpenseCache> {
        return localSource.getExpensesByCategory(category)
    }

    override suspend fun getExpensesByType(type: String): List<ExpenseCache> {
        return localSource.getExpensesByType(type)
    }

    override suspend fun addExpense(expenseCache: ExpenseCache): ExpenseCache {
        return localSource.addExpense(expenseCache)
    }

    override suspend fun addExpenses(expenses: List<ExpenseCache>) {
        localSource.addExpenses(expenses)
    }

    override suspend fun updateExpense(
        expenseCache: ExpenseCache,
        id: Long
    ): ExpenseCache {
        return localSource.updateExpense(expenseCache, id)
    }

    override suspend fun deleteExpense(id: Long) {
        localSource.deleteExpense(id)
    }
}