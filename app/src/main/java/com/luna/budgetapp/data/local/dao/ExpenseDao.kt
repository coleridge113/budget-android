package com.luna.budgetapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.luna.budgetapp.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): PagingSource<Int, ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE category = :category")
    fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE type = :type")
    fun getExpensesByType(type: String): Flow<List<ExpenseEntity>>

    @Query("""
        SELECT * FROM expenses
        WHERE date BETWEEN :start AND :end
        ORDER BY date DESC
    """)
    fun getExpensesByDateRange(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<ExpenseEntity>>

    @Query("""
        SELECT SUM(amount)
        FROM expenses
        WHERE date BETWEEN :start AND :end
    """)
    fun getTotalAmountByDateRange(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<Double>

    @Insert
    suspend fun addExpense(expense: ExpenseEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addExpenses(expenses: List<ExpenseEntity>)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpense(expenseId: Long)

    @Delete
    suspend fun deleteExpenses(expenses: List<ExpenseEntity>)

}
