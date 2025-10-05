package com.luna.budgetapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luna.budgetapp.data.local.dao.ExpenseDao
import com.luna.budgetapp.data.local.entity.ExpenseCache

@Database(entities =  [ExpenseCache::class], version =  1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}