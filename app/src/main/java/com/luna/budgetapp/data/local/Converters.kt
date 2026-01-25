package com.luna.budgetapp.data.local

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.time.LocalDateTime

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(date: LocalDateTime?) = date?.toString()

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(date: String?) = date?.let { LocalDateTime.parse(it) }
}
