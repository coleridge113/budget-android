package com.luna.budgetapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.luna.budgetapp.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import com.luna.budgetapp.common.Resource

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE category = :category")
    fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE type = :type")
    fun getExpensesByType(type: String): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun addExpense(expense: ExpenseEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addExpenses(expenses: List<ExpenseEntity>)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpenses(expenses: List<ExpenseEntity>)

}
