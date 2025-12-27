package com.luna.budgetapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.luna.budgetapp.data.local.entity.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE category = :category")
    fun getExpensesByCategory(category: String): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE type = :type")
    fun getExpensesByType(type: String): List<ExpenseEntity>

    @Insert
    fun addExpense(expenseEntity: ExpenseEntity)

    @Insert(onConflict = REPLACE)
    fun addExpenses(expenses: List<ExpenseEntity>)

    @Update
    fun updateExpense(expenseEntity: ExpenseEntity)

    @Delete
    fun deleteExpense(expenseEntity: ExpenseEntity)
}