package com.luna.budgetapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luna.budgetapp.data.local.dao.ExpenseDao
import com.luna.budgetapp.data.local.entity.ExpenseCache

@Database(entities =  [ExpenseCache::class], version =  1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}