package com.luna.budgetapp.data.remote

import com.luna.budgetapp.data.remote.dto.ExpenseDto
import com.luna.budgetapp.network.ExpenseService

class ExpenseRemoteSource(
    private val expenseService: ExpenseService
) {

    suspend fun getAllExpenses(): List<ExpenseDto> {
        return expenseService.getAllExpenses()
    }

    suspend fun getExpenseById(id: Long): ExpenseDto {
        return expenseService.getExpenseById(id)
    }

    suspend fun getExpensesByType(type: String): List<ExpenseDto> {
        return expenseService.getExpenseByType(type)
    }

    suspend fun getExpensesByCategory(category: String): List<ExpenseDto> {
        return expenseService.getExpenseByCategory(category)
    }

    suspend fun addExpense(expenseDto: ExpenseDto): ExpenseDto? {
        return expenseService.addExpense(expenseDto)
    }

    suspend fun deleteExpense(id: Long) {
        expenseService.deleteExpense(id)
    }

    suspend fun updateExpense(expenseDto: ExpenseDto, id: Long): ExpenseDto? {
        return expenseService.updateExpense(expenseDto, id)
    }
}