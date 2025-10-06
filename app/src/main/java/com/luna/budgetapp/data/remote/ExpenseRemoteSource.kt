package com.luna.budgetapp.data.remote

import android.util.Log
import com.luna.budgetapp.data.remote.dto.ExpenseDto
import com.luna.budgetapp.network.ExpenseService
import java.net.HttpURLConnection.HTTP_CREATED
import java.net.HttpURLConnection.HTTP_OK

class ExpenseRemoteSource(
    private val expenseService: ExpenseService
) {

    private val TAG = "ExpenseRemoteSource"

    suspend fun getAllExpenses(): List<ExpenseDto> {
        val response = expenseService.getAllExpenses()
        return if (response.statusCode == HTTP_OK) {
            response.body ?: emptyList()
        } else {
            Log.e(TAG, response.statusMessage)
            emptyList()
        }
    }

    suspend fun getExpenseById(id: Long): ExpenseDto? {
        val response = expenseService.getExpenseById(id)
        return if (response.statusCode == HTTP_OK && response.body != null) {
            response.body
        } else {
            Log.e(TAG, response.statusMessage)
            null
        }
    }

    suspend fun getExpensesByType(type: String): List<ExpenseDto> {
        val response = expenseService.getExpenseByType(type)
        return if(response.statusCode == HTTP_OK) {
            response.body ?: emptyList()
        } else {
            Log.e(TAG, response.statusMessage)
            emptyList()
        }
    }

    suspend fun getExpensesByCategory(category: String): List<ExpenseDto> {
        val response = expenseService.getExpenseByCategory(category)
        return if(response.statusCode == HTTP_OK) {
            response.body ?: emptyList()
        } else {
            Log.e(TAG, response.statusMessage)
            emptyList()
        }
    }

    suspend fun addExpense(expenseDto: ExpenseDto): ExpenseDto? {
        val response = expenseService.addExpense(expenseDto)
        return if (response.statusCode == HTTP_CREATED) {
            response.body
        } else {
            Log.e(TAG, response.statusMessage)
            null
        }
    }

    suspend fun deleteExpense(id: Long): Boolean {
        val response = expenseService.deleteExpense(id)
        return (if (response.statusCode == HTTP_OK) {
            response.body
        } else {
            Log.e(TAG, response.statusMessage)
            false
        }) == true
    }

    suspend fun updateExpense(expenseDto: ExpenseDto, id: Long): ExpenseDto? {
        val response = expenseService.updateExpense(expenseDto, id)
        return if (response.statusCode == HTTP_OK) {
            response.body
        } else {
            Log.e(TAG, response.statusMessage)
            null
        }
    }
}