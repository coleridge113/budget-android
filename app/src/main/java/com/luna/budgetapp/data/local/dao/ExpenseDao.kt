package com.luna.budgetapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luna.budgetapp.data.local.entity.ExpenseCache

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): List<ExpenseCache>

    @Query("SELECT * FROM expenses WHERE category = :category")
    fun getExpensesByCategory(category: String): List<ExpenseCache>

    @Query("SELECT * FROM expenses WHERE type = :type")
    fun getExpensesByType(type: String): List<ExpenseCache>

    @Insert
    fun addExpense(expenseCache: ExpenseCache)

    @Update
    fun updateExpense(expenseCache: ExpenseCache, id: Long)

    @Delete
    fun deleteExpense(id: Long)
}