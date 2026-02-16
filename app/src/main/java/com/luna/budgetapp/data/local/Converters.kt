package com.luna.budgetapp.data.local

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Converters {

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): Long? {
        return value
            ?.atZone(ZoneId.systemDefault())
            ?.toInstant()
            ?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? {
        return value?.let {
            Instant.ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        }
    }
}
