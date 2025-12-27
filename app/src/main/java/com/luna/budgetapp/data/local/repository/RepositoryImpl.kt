package com.luna.budgetapp.data.local.repository

import com.luna.budgetapp.data.local.dao.ExpenseDao
import com.luna.budgetapp.data.mapper.toCache
import com.luna.budgetapp.data.mapper.toModel
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.repository.ExpenseRepository

class RepositoryImpl(
    private val dao: ExpenseDao,
) : ExpenseRepository {

    override suspend fun getAllExpenses(): List<Expense> {
        return dao.getAllExpenses().map { it.toModel() }
    }

    override suspend fun getExpensesByCategory(category: String): List<Expense> {
        return dao.getExpensesByCategory(category).map { it.toModel() }
    }

    override suspend fun getExpensesByType(type: String): List<Expense> {
        return dao.getExpensesByType(type).map { it.toModel() }
    }

    override suspend fun addExpense(expense: Expense) {
        return dao.addExpense(expense.toCache())
    }

    override suspend fun addExpenses(expenses: List<Expense>) {
        dao.addExpenses(expenses.map { it.toCache() })
    }

    override suspend fun updateExpense(expense: Expense) {
        return dao.updateExpense(expense.toCache())
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense.toCache())
    }
}
