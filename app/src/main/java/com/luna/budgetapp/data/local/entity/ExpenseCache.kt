package com.luna.budgetapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "expenses")
data class ExpenseCache(
    @PrimaryKey val id: Long,
    @ColumnInfo(name =  "name") val name: String?,
    @ColumnInfo(name =  "amount") val amount: Double,
    @ColumnInfo(name =  "category") val category: String,
    @ColumnInfo(name =  "type") val type: String,
    @ColumnInfo(name =  "date") val date: String
)
