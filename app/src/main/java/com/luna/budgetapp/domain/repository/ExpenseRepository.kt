package com.luna.budgetapp.domain.repository

import com.luna.budgetapp.domain.model.Expense

interface ExpenseRepository {
    suspend fun getAllExpenses(): List<Expense>

    suspend fun getExpensesByCategory(category: String): List<Expense>

    suspend fun getExpensesByType(type: String): List<Expense>

    suspend fun addExpense(expense: Expense)

    suspend fun addExpenses(expenses: List<Expense>)

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}
