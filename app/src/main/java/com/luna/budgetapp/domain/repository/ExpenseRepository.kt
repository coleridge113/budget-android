package com.luna.budgetapp.domain.repository

import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.common.Resource
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun getAllExpenses(): Flow<Resource<List<Expense>>>

    suspend fun getExpensesByCategory(category: String): Flow<Resource<List<Expense>>>

    suspend fun getExpensesByType(type: String): Flow<Resource<List<Expense>>>

    suspend fun addExpense(expense: Expense)

    suspend fun addExpenses(expenses: List<Expense>)

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}
