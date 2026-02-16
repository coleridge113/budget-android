package com.luna.budgetapp.domain.repository

import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.common.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ExpenseRepository {
    fun getAllExpenses(): Flow<Resource<List<Expense>>>

    fun getExpensesByCategory(category: String): Flow<Resource<List<Expense>>>

    fun getExpensesByType(type: String): Flow<Resource<List<Expense>>>

    fun getExpensesByDateRange(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<Resource<List<Expense>>>

    suspend fun addExpense(expense: Expense)

    suspend fun addExpenses(expenses: List<Expense>)

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}
