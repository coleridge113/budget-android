package com.luna.budgetapp.data.local

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.time.LocalDate

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromBigDecimal(value: BigDecimal?) = value?.toPlainString()

    @TypeConverter
    @JvmStatic
    fun toBigDecimal(value: String?) = value?.toBigDecimal()

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?) = date?.toString()

    @TypeConverter
    @JvmStatic
    fun toLocalDate(date: String?) = date?.let { LocalDate.parse(it) }
}