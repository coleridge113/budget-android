package com.luna.budgetapp.data.local.repository

import com.luna.budgetapp.data.local.entity.ExpenseCache

interface Repository {

    suspend fun getAllExpenses(): List<ExpenseCache>

    suspend fun getExpensesByCategory(category: String): List<ExpenseCache>

    suspend fun getExpensesByType(type: String): List<ExpenseCache>

    suspend fun addExpense(expenseCache: ExpenseCache)

    suspend fun updateExpense(expenseCache: ExpenseCache, id: Long)

    suspend fun deleteExpense(id: Long)
}